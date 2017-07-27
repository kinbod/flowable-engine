/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.dmn.engine.impl.hitpolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.dmn.api.RuleExecutionAuditContainer;
import org.flowable.dmn.engine.impl.el.ELExecutionContext;
import org.flowable.dmn.engine.impl.util.CommandContextUtil;
import org.flowable.dmn.model.HitPolicy;
import org.flowable.engine.common.api.FlowableException;

/**
 * @author Yvo Swillens
 */
public class HitPolicyUnique extends AbstractHitPolicy implements EvaluateRuleValidityBehavior, ComposeDecisionResultBehavior {

    @Override
    public String getHitPolicyName() {
        return HitPolicy.UNIQUE.getValue();
    }

    @Override
    public void evaluateRuleValidity(int ruleNumber, ELExecutionContext executionContext) {
        //TODO: not on audit container
        for (Map.Entry<Integer, RuleExecutionAuditContainer> entry : executionContext.getAuditContainer().getRuleExecutions().entrySet()) {
            if (entry.getKey().equals(ruleNumber) == false && entry.getValue().isValid()) {
                String hitPolicyViolatedMessage = String.format("HitPolicy UNIQUE violated: rule %d is valid but rule %d was already valid", ruleNumber, entry.getKey());

                if (CommandContextUtil.getDmnEngineConfiguration().isStrictMode()) {
                    executionContext.getAuditContainer().getRuleExecutions().get(ruleNumber).setExceptionMessage(hitPolicyViolatedMessage);
                    throw new FlowableException("HitPolicy UNIQUE violated");
                }
            }
        }
    }

    public void composeDecisionResults(ELExecutionContext executionContext) {
        List<Map<String, Object>> ruleResults = new ArrayList<>(executionContext.getRuleResults().values());
        List<Map<String, Object>> decisionResult = null;

        if (CommandContextUtil.getDmnEngineConfiguration().isStrictMode() == false) {
            Map<String, Object> lastResult = new HashMap<>();

            for (Map<String, Object> ruleResult : ruleResults) {
                for (Map.Entry<String, Object> entry : ruleResult.entrySet()) {
                    if (entry.getValue() != null) {
                        lastResult.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            decisionResult = Collections.singletonList(lastResult);
            
        } else {
            decisionResult = ruleResults;
        }

        executionContext.getAuditContainer().setDecisionResult(decisionResult);
    }
}

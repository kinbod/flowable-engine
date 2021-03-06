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
package org.flowable.variable.service.event.impl;

import org.flowable.engine.common.api.delegate.event.FlowableEngineEventType;
import org.flowable.engine.common.impl.event.FlowableEventImpl;
import org.flowable.variable.service.event.FlowableVariableEvent;
import org.flowable.variable.service.impl.types.VariableType;

/**
 * Implementation of {@link FlowableVariableEvent}.
 * 
 * @author Frederik Heremans
 */
public class FlowableVariableEventImpl extends FlowableEventImpl implements FlowableVariableEvent {

    protected String variableName;
    protected Object variableValue;
    protected VariableType variableType;
    protected String taskId;

    public FlowableVariableEventImpl(FlowableEngineEventType type) {
        super(type);
    }

    @Override
    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Object getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(Object variableValue) {
        this.variableValue = variableValue;
    }

    @Override
    public VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

}

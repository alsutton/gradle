/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

package org.gradle.api.internal.project.taskfactory;

import org.gradle.api.internal.TaskOutputsInternal;
import org.gradle.api.tasks.TaskOutputFilePropertyBuilder;

import java.util.Collection;
import java.util.concurrent.Callable;

public abstract class AbstractOutputPropertyAnnotationHandler implements PropertyAnnotationHandler {

    public void attachActions(final TaskPropertyActionContext context) {
        context.setValidationAction(new ValidationAction() {
            @Override
            public void validate(String propertyName, Object value, Collection<String> messages) {
                AbstractOutputPropertyAnnotationHandler.this.validate(propertyName, value, messages);
            }
        });
        context.setConfigureAction(new UpdateAction() {
            @Override
            public void updateOutputs(TaskOutputsInternal outputs, String propertyName, final Callable<Object> futureValue) {
                createPropertyBuilder(outputs, futureValue)
                    .withPropertyName(propertyName)
                    .optional(context.isOptional());
            }
        });
    }

    protected abstract TaskOutputFilePropertyBuilder createPropertyBuilder(TaskOutputsInternal outputs, Callable<Object> futureValue);

    protected abstract void validate(String propertyName, Object value, Collection<String> messages);
}

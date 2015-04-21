/*
 * Copyright 2015 Codice Foundation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.codice.testify.assertions.NotContains;

import org.codice.testify.assertions.Assertion;
import org.codice.testify.objects.AssertionStatus;
import org.codice.testify.objects.TestifyLogger;
import org.codice.testify.objects.Response;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The NotContainsAssertion class is a Testify Assertion service that checks that a string is not contained in the response
 */
public class NotContainsAssertion implements BundleActivator, Assertion {

    @Override
    public AssertionStatus evaluateAssertion(String assertionInfo, Response response) {

        TestifyLogger.debug("Running NotContainsAssertion", this.getClass().getSimpleName());

        //Get the processor response
        String responseValue = response.getResponse();
        AssertionStatus status;

        //If no assertion info is provided, return a failure
        if (assertionInfo == null) {
            status = new AssertionStatus("No string provided with assertion");

        //If the response from the processor is null, return a failure
        } else if (responseValue == null) {
            status = new AssertionStatus("No processor response");

        } else {

            //If the response does not contains the assertion info, return failure details of null meaning a successful assertion
            if (!responseValue.contains(assertionInfo)) {
                status = new AssertionStatus(null);

            //If the response does contain the assertion info, return a failure
            } else {
                status = new AssertionStatus("Response contains the assertion string");
            }
        }
        return status;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {

        //Register the NotContains service
        bundleContext.registerService(Assertion.class.getName(), new NotContainsAssertion(), null);

    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
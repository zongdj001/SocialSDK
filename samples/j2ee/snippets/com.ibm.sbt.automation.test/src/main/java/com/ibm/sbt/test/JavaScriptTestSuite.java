/*
 * © Copyright IBM Corp. 2013
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package com.ibm.sbt.test;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibm.sbt.automation.core.environment.TestEnvironment;
import com.ibm.sbt.test.js.AuthenticationTestSuite;
import com.ibm.sbt.test.js.BaseTestSuite;
import com.ibm.sbt.test.js.ConnectionsTestSuite;
import com.ibm.sbt.test.js.ToolkitTestSuite;

/**
 * @author mwallace
 * 
 * @date 12 Mar 2013
 */
@RunWith(Suite.class)
@SuiteClasses({ AuthenticationTestSuite.class, BaseTestSuite.class, ToolkitTestSuite.class, ConnectionsTestSuite.class })
public class JavaScriptTestSuite {
    @AfterClass
    public static void cleanup() {
        TestEnvironment.cleanup();
    }
}

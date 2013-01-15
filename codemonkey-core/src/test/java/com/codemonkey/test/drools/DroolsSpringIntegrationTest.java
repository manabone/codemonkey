package com.codemonkey.test.drools;

import com.codemonkey.test.service.AbsServiceTest;

public class DroolsSpringIntegrationTest extends AbsServiceTest{

//	@Autowired StatefulKnowledgeSession ksession;
//    
//	@Test
//    public void springIntegration() {
//		Assert.assertNotNull(ksession);
//        ProcessInstance processInstance = ksession.startProcess("org.jbpm.test");
//        Assert.assertNotNull(processInstance);
//        long processInstance1Id = processInstance.getId();
//        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, processInstance.getState());
//        ksession.signalEvent("continueSignal", null, processInstance1Id);
//        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, processInstance.getState());
//    }

}
package jp.terasoluna.thin.tutorial.web.helloworld.blogic;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.service.thin.BLogic;
import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.thin.tutorial.web.helloworld.dto.HelloWorldInput;
import jp.terasoluna.thin.tutorial.web.helloworld.dto.HelloWorldOutput;

public class HelloWorldBLogic implements BLogic<HelloWorldInput> {

	private QueryDAO queryDAO;

	public void setQueryDAO(QueryDAO queryDAO) {
		this.queryDAO = queryDAO;
	}

	public BLogicResult execute(HelloWorldInput param) {
		BLogicResult result = new BLogicResult();
		
		HelloWorldOutput helloWorldOutput = queryDAO.executeForObject(
				"helloWorld", param, HelloWorldOutput.class);
		
		result.setResultObject(helloWorldOutput);
		result.setResultString("success");

		return result;
	}
}

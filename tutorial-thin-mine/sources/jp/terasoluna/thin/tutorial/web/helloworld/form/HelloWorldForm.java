package jp.terasoluna.thin.tutorial.web.helloworld.form;

import jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx;

public class HelloWorldForm extends ValidatorActionFormEx {

	private static final long serialVersionUID = 1L;

	private String helloWorld;

	public String getHelloWorld() {
		return helloWorld;
	}

	public void setHelloWorld(String helloWorld) {
		this.helloWorld = helloWorld;
	}
	
}

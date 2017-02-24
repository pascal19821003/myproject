package sinc.tests.mail;

public class TestMail {
	public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("10.200.60.117");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("zidonghua");
		mailInfo.setPassword("Spdb1234");// 您的邮箱密码
		mailInfo.setFromAddress("zidonghua@spdb.com.cn");
		mailInfo.setToAddress("asstsys07@spdb.com.cn");
		mailInfo.setSubject("This is test only");
		mailInfo.setContent("FYI-----------------------");
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
		System.out.println("sucess");
	}
}

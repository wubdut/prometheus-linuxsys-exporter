package com.LinuxSysExporter.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang.StringUtils;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
  
/** 
 * 远程执行linux的shell script
 * @author Bin Wu 
 * @since  V1.0
 */  
public class RemoteExecute {
    //字符编码默认是utf-8
    private static String  DEFAULTCHART="UTF-8";
    private static RemoteExecute instance = null;
    private static boolean netStatus = false;
    
    private Connection conn;
    
    private RemoteExecute() {
    	conn = new Connection(Configuration.ip);
    	try {
			ConnectionInfo info = conn.connect();
//			System.out.println("info: " + info.clientToServerMACAlgorithm);
			netStatus = conn.authenticateWithPassword(Configuration.userName, Configuration.userPwd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("网络连接失败");
			netStatus = false;
		}
    }
    
    /** 
     * 检查远程linux主机连接
     * @author Bin Wu
     * @since  V1.0
     * @return 
     *      连接返回true，否则返回false 
     */  
//    public boolean login() {
//    	boolean flag = false;
//    	try {
//			flag = conn.authenticateWithPassword(Configuration.userName, Configuration.userPwd);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("验证登录失败");
//			return false;
//		}
//    	return flag;
//    }
    
    
    /** 
     * @author Bin Wu
     * 远程执行shll脚本或者命令 
     * @param cmd 
     *      即将执行的命令 
     * @return
     *      命令执行完后返回的结果值 
     * @since V1.0
     */  
    public String execute(String cmd){
        String result="";
        Session session = null;
        try {
            if(netStatus){
                session= conn.openSession();//打开一个会话
                session.execCommand(cmd);//执行命令
                result=processStdoutString(session.getStdout(),DEFAULTCHART);
                //如果为得到标准输出为空，说明脚本执行出错了
                if(StringUtils.isBlank(result)){
                    result=processStdoutString(session.getStderr(),DEFAULTCHART);
                }
            } else {
            	return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	if (session != null)
        		session.close();
        }
        
        return result;
    }
    
   /**
    * 解析脚本执行返回的结果集
    * @author Ickes
    * @param in 输入流对象
    * @param charset 编码
    * @since V0.1
    * @return
    *       以纯文本的格式返回
    */
    private String processStdoutString(InputStream in, String charset){
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout,charset));
            String line=null;
            while((line=br.readLine()) != null){
                buffer.append(line+"\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    
    public static RemoteExecute getInstance() {
    	if (instance == null || !netStatus) {
    		if (instance != null) 
    			instance.finalize();
    		instance = new RemoteExecute();
    	}
    	return instance;
    }
    
    @Override
	protected  void finalize() {
    	if (conn != null)
    		conn.close();
    }  
    
}

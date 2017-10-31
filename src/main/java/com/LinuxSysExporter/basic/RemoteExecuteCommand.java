package com.LinuxSysExporter.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang.StringUtils;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
  
/** 
 * 远程执行linux的shell script
 * @author Ickes
 * @since  V0.1
 */  
public class RemoteExecuteCommand {
    //字符编码默认是utf-8
    private static String  DEFAULTCHART="UTF-8";
    private Connection conn;
    private String ip;
    private String userName;
    private String userPwd;
    
    public RemoteExecuteCommand(String ip, String userName, String userPwd) {
        this.ip = ip;
        this.userName = userName;
        this.userPwd = userPwd;
    }
    
    public RemoteExecuteCommand() {  
          
    }  
      
    /** 
     * 远程登录linux的主机 
     * @author Ickes 
     * @since  V0.1 
     * @return 
     *      登录成功返回true，否则返回false 
     */  
    public Boolean login(){
        boolean flg=false;
        conn = null;
        while (true) {
	        try {
	            conn = new Connection(ip);
	            conn.connect();//连接
	            flg=conn.authenticateWithPassword(userName, userPwd);//认证
	            break;
	        } catch (IOException e) {
	        	if (conn != null) {
	        		conn.close();
	        	}
	        	System.out.println("无法 连接 或者 登陆  主机");
	        	try {
					Thread.sleep(1000*6);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            e.printStackTrace();
	        }
        }
        return flg;
    }
    /** 
     * @author Ickes 
     * 远程执行shll脚本或者命令 
     * @param cmd 
     *      即将执行的命令 
     * @return
     *      命令执行完后返回的结果值 
     * @since V0.1
     */  
    public String execute(String cmd){
        String result="";
        Session session = null;
        while (true) {
	        try {
	            if(login()){
	                session= conn.openSession();//打开一个会话
	                session.execCommand(cmd);//执行命令
	                result=processStdoutString(session.getStdout(),DEFAULTCHART);
	                //如果为得到标准输出为空，说明脚本执行出错了
	                if(StringUtils.isBlank(result)){
	                    result=processStdoutString(session.getStderr(),DEFAULTCHART);
	                }
	            }
	            break;
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	        	if (session != null) {
	        		session.close();
	        	}
	        	if (conn != null) {
	        		conn.close();
	        	}
	        }
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
    
    
    public static void setCharset(String charset) {
        DEFAULTCHART = charset;
    }
    public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPwd() {
        return userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    
    
    public static void main(String[] args) {
        
    }
}

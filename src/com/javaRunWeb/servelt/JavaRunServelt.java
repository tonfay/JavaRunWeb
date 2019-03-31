package com.javaRunWeb.servelt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class JavaRunServelt
 */
@WebServlet("/run")
public class JavaRunServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Process process;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置字符编码为UTF-8,前台页面支持汉字显示
		resp.setCharacterEncoding("UTF-8");
		String code = req.getParameter("code");
	// 获取页面参数
		PrintWriter pw = resp.getWriter();
		String className = null; // 类名
		String classStr = null;
		BufferedWriter bw = null;
		try {
			classStr = code.substring(code.indexOf("public class"), code.indexOf("{")).toString();// 获取类名字符串
			String[] classStrArray = classStr.split("\\s{1,}");// 按空格分开
			if (classStrArray.length != 3) {
				req.setAttribute("msg", "编译失败：格式不符合规范，请检查类名是否正确(如：public class YouClassName{})"); 
			} else {
				className = classStrArray[classStrArray.length - 1];
				File sourceFile = new File(className + ".java");// 保存源代码
				if (sourceFile.exists()) {
					sourceFile.delete();
				}
				FileWriter fr = new FileWriter(sourceFile);
				bw = new BufferedWriter(fr);
				bw.write(code);
				bw.close();
				fr.close();

				Runtime runtime = Runtime.getRuntime();
				process = runtime.exec("cmd");
				Thread.sleep(1000);// 防止cmd.exe程序未启动，当然也可以直接使用javac命令
				// 往控制台注入命令
				bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
				bw.write("javac " + className + ".java \n");
				bw.flush();
				long startFreeMemory = runtime.freeMemory();// Java 虚拟机中的空闲内存量
				// 执行时间也是无法知道，因为dos执行java命令，程序无法知道它到底执行到那里了，两个进程，互不了解
				long startCurrentTime = System.currentTimeMillis();// 获取系统当前时间
				bw.write("java " + className + " \n");
				bw.close();
				// 获取控制台输出的结果
				Thread runtimeInput = new Thread(new RuntimeInput(req));
				runtimeInput.start();

				// 获取内存信息,实际过程中，是无法得到这个程序到底多少内存，内存的分配有操作系统决定，如果
				// 程序需要，系统会动态分配内存，如果有对象没有引用，可能会被垃圾回收器回收，所以是无法得到到底多少内存的
				// 现在得到程序运行前后内存使用率，不过输出的是0，因为程序退出，不占内存了
				Thread.sleep(1000);
				long endCurrentTime = System.currentTimeMillis();
				long endFreeMemory = runtime.freeMemory();
				double useMemory = (startFreeMemory - endFreeMemory) / 1024;
				System.out.println("开始时间:" + startCurrentTime);
				System.out.println("结束时间:" + endCurrentTime);
				long useTime = endCurrentTime - startCurrentTime;
				
				req.setAttribute("arg", "运行成功,运行时间为:"+useTime);

			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg","格式不符合规范，请检查类名是否正确(如：public class YouClassName{}).错误信息:" + e.getMessage());
		}
		 req.getRequestDispatcher("toStudy.jsp").forward(req, resp);  
	}

	public class RuntimeInput implements Runnable {
		HttpServletRequest request;
		HttpServletResponse response;
		
		public RuntimeInput(HttpServletRequest request){
			this.request=request;
		}
		public RuntimeInput(HttpServletResponse response){
			this.response=response;
		}
		@Override
		public void run() {
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader be = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			StringBuffer sb = new StringBuffer();
			String content = null;
			String error = null;
			String code = request.getParameter("code");
			String className = null;
			String classStr = null;
			classStr = code.substring(code.indexOf("public class"),code.indexOf("{")).toString();
			String[] classStrArray = classStr.split("\\s{1,}");
			className = classStrArray[classStrArray.length-1];
			int i = 0;
			try {
				while((error = be.readLine()) != null){
			    	System.out.println(error);
			    	sb.append(error+"\n");
			    	i++;
			    }
				String pr=sb.toString();				
				request.setAttribute("msg","运行失败,请看错误提示\n"+pr);
				 if(i==0){
			    	while((content = br.readLine()) != null){
			            	
			            	sb.append(content+"\n");
			    	}
			    	pr=sb.toString();
			    	pr=pr.substring(pr.indexOf("java "+className)+(5+className.length()),pr.lastIndexOf("E:"));
			    	
			    	
				    //response.setContentType("text/html;charset=utf-8");
			        //PrintWriter out1=response.getWriter();
			    	request.setAttribute("msg","运行成功,结果为"+pr);
			        System.out.println("程序结果为:"+pr);//
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
			

	}
}
}
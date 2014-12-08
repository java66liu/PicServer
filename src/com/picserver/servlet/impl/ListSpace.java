package com.picserver.servlet.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.SpaceBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.utils.JsonUtil;

/**
 * 查询某uid的所有space
 */
@WebServlet("/ListSpace")
public class ListSpace extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListSpace() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		HbaseReader hr = new HbaseReader();
		response.setCharacterEncoding("utf-8");
		try {
			List<SpaceBean> list = hr.getSpaceBean("attr","uid", uid);
			String res = JsonUtil.createJsonString("Spaces", list);
			PrintWriter out = response.getWriter();
			out.write(res);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

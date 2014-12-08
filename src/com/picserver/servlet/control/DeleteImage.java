package com.picserver.servlet.control;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;
import com.picserver.hbase.HbaseReader;
import com.picserver.hbase.HbaseWriter;
import com.picserver.hdfs.HdfsUtil;
import com.picserver.hdfs.MapfileUtils;

/**
 * Servlet implementation class DeleteImage
 */
@WebServlet("/DeleteImage")
public class DeleteImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String BigFile="HdfsLargeFile";
    private static final String SmallFile="HdfsSmallFile";
    private static final String LocalFile="LocalFile";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获取图片信息和相对应的sapce信息，并对space进行修改
		String rowkey=request.getParameter("image");
		HbaseReader reader=new HbaseReader();
		PictureBean pic= reader.getPictureBean(rowkey);
		String space_key=pic.getSpace();
		SpaceBean space=reader.getSpaceBean(space_key);
		int number=Integer.parseInt(space.getNumber())-1;
		space.setNumber(Integer.toString(number));
		double size=Double.parseDouble(space.getStorage())-Double.parseDouble(pic.getSize());
		space.setStorage(Double.toString(size));
		//将修改后的space更新到数据库
		HbaseWriter writer=new HbaseWriter();
		writer.putSpaceBean(space);
		//根据图片状态的不同采取不同的方式
		String status=pic.getStatus();
		

		if(status.equals(BigFile)){
			HdfsUtil hd=new HdfsUtil();
			hd.deletePath(pic.getPath());
			writer.deletePictureBean(pic);
			System.out.println("大文件删除成功");
		}
		else if(status.equals(SmallFile)){
//			MapfileUtils mf=new MapfileUtils();
//			mf.deleteImage(hdfsDir, images);
			
		}
		else if(status.equals(LocalFile)){
			File f=new File(pic.getPath(),pic.getName());
			if(f.exists())
			{
				f.delete();
				writer.deletePictureBean(pic);
				System.out.println("本地文件删除成功");
			}
				
			
		}
	}

}

package com.picserver.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

import com.picserver.bean.PictureBean;
import com.picserver.bean.SpaceBean;

/**
 * 读取hbase内容封装到Bean中
 * @author hadoop
 *
 */
public class HbaseReader {

	HbaseOperation ho = new HbaseOperation();

	/**
	 * 根据rowkey读取数据保存在PictureBean
	 * @param rowkey,图片名
	 * @return 存在则返回Bean，不存在返回null
	 */
	public PictureBean getPictureBean(String rowkey) {
		PictureBean pb = new PictureBean();
		Result rs = ho.QueryByRowKey("cloud_picture", rowkey);
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			pb.setName(rowkey);
			for (KeyValue keyValue : rs.raw()) {
				String v = new String(keyValue.getQualifier());
				String val = new String(keyValue.getValue());
				if (v.equals("size")) {
					pb.setSize(val);
				}
				if (v.equals("type")) {
					pb.setType(val);
				}
				if (v.equals("space")) {
					pb.setSpace(val);
				}
				if (v.equals("usr")) {
					pb.setUsr(val);
				}
				if (v.equals("createTime")) {
					pb.setCreateTime(val);
				}
				if (v.equals("isCloud")) {
					pb.setIsCloud(val);
				}
				if (v.equals("updateTime")) {
					pb.setUpdateTime(val);
				}
				if (v.equals("visitCount")) {
					pb.setVisitCount(val);
				}
				if (v.equals("visitFlow")) {
					pb.setVisitFlow(val);
				}
			}
		}
		return pb;
	}

	/**
	 * 根据图片列值检索表cloud_picture
	 * @param family  列族
	 * @param column 列
	 * @param value 值
	 * @return 如果检索到，则返回PictureBean的list，没有则返回null
	 * @throws IOException
	 */
	public List<PictureBean> getPictureBean(String family, String column,
			String value) throws IOException {
		List<PictureBean> list = new ArrayList<PictureBean>();
		PictureBean pb = new PictureBean();
		ResultScanner rs = ho.QueryByColumn("cloud_picture", family, column,
				value);
		for (Result r : rs) {
			pb.setName(new String(r.getRow()));
			for (KeyValue keyValue : r.raw()) {
				String v = new String(keyValue.getQualifier());
				String val = new String(keyValue.getValue());
				if (v.equals("size")) {
					pb.setSize(val);
				}
				if (v.equals("type")) {
					pb.setType(val);
				}
				if (v.equals("space")) {
					pb.setSpace(val);
				}
				if (v.equals("usr")) {
					pb.setUsr(val);
				}
				if (v.equals("createTime")) {
					pb.setCreateTime(val);
				}
				if (v.equals("isCloud")) {
					pb.setIsCloud(val);
				}
				if (v.equals("updateTime")) {
					pb.setUpdateTime(val);
				}
				if (v.equals("visitCount")) {
					pb.setVisitCount(val);
				}
				if (v.equals("visitFlow")) {
					pb.setVisitFlow(val);
				}
			}
			list.add(pb);
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}

	/**
	 * 根据rowkey读取数据保存在SpaceBean
	 * @param rowkey 
	 * @return 存在则返回Bean，不存在返回null
	 */
	public SpaceBean getSpaceBean(String rowkey) {
		SpaceBean sb = new SpaceBean();
		Result rs = ho.QueryByRowKey("cloud_space", rowkey);
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			sb.setName(rowkey);
			for (KeyValue keyValue : rs.raw()) {
				String v = new String(keyValue.getQualifier());
				String val = new String(keyValue.getValue());
				if (v.equals("desc")) {
					sb.setDesc(val);
				}
				if (v.equals("cover")) {
					sb.setCover(val);
				}
				if (v.equals("storage")) {
					sb.setStorage(val);
				}
				if (v.equals("number")) {
					sb.setNumber(val);
				}
				if (v.equals("flow")) {
					sb.setFlow(val);
				}
			}
		}
		return sb;
	}

	/**
	 * 根据空间列值检索表cloud_picture
	 * @param family 列族
	 * @param column 列
	 * @param value 值
	 * @return 根据图片列值检索表cloud_picture
	 * @throws IOException
	 */
	public List<SpaceBean> getSpaceBean(String family, String column,
			String value) throws IOException {
		List<SpaceBean> list = new ArrayList<SpaceBean>();
		SpaceBean sb = new SpaceBean();
		ResultScanner rs = ho.QueryByColumn("cloud_space", family, column,
				value);
		for (Result r : rs) {
			sb.setName(new String(r.getRow()));
			for (KeyValue keyValue : r.raw()) {
				String v = new String(keyValue.getQualifier());
				String val = new String(keyValue.getValue());
				if (v.equals("desc")) {
					sb.setDesc(val);
				}
				if (v.equals("cover")) {
					sb.setCover(val);
				}
				if (v.equals("storage")) {
					sb.setStorage(val);
				}
				if (v.equals("number")) {
					sb.setNumber(val);
				}
				if (v.equals("flow")) {
					sb.setFlow(val);
				}
			}
			list.add(sb);
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
}

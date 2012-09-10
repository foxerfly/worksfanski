/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edwin.sql;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;

/**
 * 
 * @author Administrator
 */
public class QueryErp {
	/**
	 * Logger for this class
	 */

	/**
	 * Created by IntelliJ IDEA. User: Administrator Date: 11-5-19 Time: 上午10:12
	 * <p/>
	 * 数据链接 To change this template use File | Settings | File Templates.
	 */

	// private String url = "jdbc:sqlserver://localhost:1433;databasename=ACM";
	// private String name = "sa";
	// private String password = "86916503";

	// private String url =
	// "jdbc:sqlserver://192.168.2.254:1433;databasename=roadage12";
	// private String name = "1";
	// private String password = "newton";

	// private String url =
	// "jdbc:sqlserver://192.168.2.254:1433;databasename=testcount2";
	// private String name = "sa";
	// private String password = "roadagefy";
	//
	private String url = "";
	private String name = "";
	private String password = "";
	private String forname = "";
	//
	private boolean stat = false;
	private Integer i = 0;
	private Connection conn;

	// sql2008 : forname:com.microsoft.sqlserver.jdbc.SQLServerDriver Sql
	// :jdbc:sqlserver://192.168.2.254:1433;databasename=roadage12
	// sql2000 : froname:com.microsoft.jdbc.sqlserver.SQLServerDriver Sql
	// :jdbc:microsoft:sqlserver://192.168.8.29:1433;DatabaseName=TradeCRM5

	public void SetConnPre(ArrayList<String> list) {

		this.forname = list.get(0); // jdbc连接
		this.url = list.get(1); // jdbc连接
		this.name = list.get(2); // name
		this.password = list.get(3); // password

	}

	public QueryErp() throws ClassNotFoundException, SQLException {

		ArrayList<String> al = new ArrayList<String>();
		
//		al.add("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//		al.add("jdbc:sqlserver://127.0.0.1:1433;databasename=fanski");
//		al.add("sa");
//		al.add("86916503");
		
		al.add("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		al.add("jdbc:sqlserver://192.168.1.4:1433;databasename=fanski");
		al.add("sa");
		al.add("fanski=123");

		SetConnPre(al);
		connErp();
	}

	public QueryErp(ArrayList<String> list) throws ClassNotFoundException,
			SQLException {

		SetConnPre(list);
		connErp();
	}

	// 创建连接connection
	public Connection connErp() throws ClassNotFoundException, SQLException {

		Class.forName(forname);
		conn = DriverManager.getConnection(url, name, password);
		return conn;
	}

	// INSERT 数据
		public boolean insertErp(String sql) throws SQLException {

			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// pstmt.setString(1, ((String) list.get(0)).trim());// creator
			
			i = pstmt.executeUpdate();
			// System.out.println(i);

			if (i >= 1||i==0) {

				stat = true;
			} else {
				stat = false;
			}

			return stat;
		}
	
	// INSERT 数据
	public boolean insertErp(String sql,ArrayList list) throws SQLException {

		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		// pstmt.setString(1, ((String) list.get(0)).trim());// creator
		
		int lengthOfList;
		lengthOfList =list.size();
		
		for(int i=0;i<lengthOfList ;i++)
				pstmt.setString(i+1, ((String) list.get(i)).trim());

//		pstmt.setString(1, ((String) list.get(0)).trim());
//		pstmt.setString(2, ((String) list.get(1)).trim());
//		pstmt.setString(3, ((String) list.get(2)).trim());
//		pstmt.setString(4, ((String) list.get(3)).trim());
//		pstmt.setString(5, ((String) list.get(4)).trim());
//		pstmt.setString(6, ((String) list.get(5)).trim());
		
		
		i = pstmt.executeUpdate();
		// System.out.println(i);

		if (i >= 1) {

			stat = true;
		} else {
			stat = false;
		}

		return stat;
	}
	
	
	// INSERT 数据
	public boolean insertErp(ArrayList list) throws SQLException {

		String sql = "INSERT INTO \n"
				+ "MYNOTTA(CREATOR,CREATE_DATE,MODIFIER,MODI_DATE,FLAG,TA001,TA003,TA004,TA005,TA006,TA009,TA010,TA022,UDF01,UDF02,UDF03,UDF04,UDF05)\n"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		// pstmt.setString(1, ((String) list.get(0)).trim());// creator

		i = pstmt.executeUpdate();
		// System.out.println(i);

		if (i == 1) {

			stat = true;
		} else {
			stat = false;
		}

		return stat;
	}
	
	
	public boolean rsErpUpdate(String sql) throws SQLException {
		
		boolean booboo=false;
		
		PreparedStatement pstmt=null;
		pstmt = conn.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		if(pstmt.executeUpdate()>=1)
			return true;
		else return false;
	}

	// 更新CRM表F_SC 回写CustPoNo list: [0] CustPoNo [1]SCNo
	public boolean updateCRMsingleton(ArrayList<String> list)
			throws SQLException {
		try {
			String sql = "UPDATE F_SC SET CustPoNo=? WHERE SCNo=? AND SCState in('0','1','2')";
			int j = 1;
			PreparedStatement pstmt = null;
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// 循环取得各组数据[[2201-120601001,test120609001],[2201-120601001,RA12L054],[...,...]]
			for (String s : list) {
				pstmt.setString(j, s);
				j++;
			}
			j = 1;
			// pstmt.addBatch();
			pstmt.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			return true;
		} catch (Exception e) {
			try {
				conn.rollback();
				// 测试输出
				for (String i : list) {
				}
				e.getCause();
				return false;
			} catch (SQLException e1) {
			}
		} finally {
			// closePstmt(pstmt);
			// pstmtHead.close
			// conn.close();
		}
		return false;
	}

	// 更新CRM表F_SC 多表
	public boolean updateCRM(ArrayList<ArrayList<String>> list)
			throws SQLException {

		try {
			String sql = "UPDATE TradeCRM5 SET CustPoNo=? WHERE SCNo=? AND SCState in('0','1','2')";
			int j = 1;
			PreparedStatement pstmt = null;
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// 循环取得各组数据[[2201-120601001,test120609001],[2201-120601001,RA12L054],[...,...]]
			for (ArrayList<String> l : list) {
				for (String s : l) {
					pstmt.setString(j, s);
					j++;
				}
				j = 1;
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			return true;
		} catch (Exception e) {
			try {
				conn.rollback();
				// System.out.println(list);
				e.getCause();
				return false;
			} catch (SQLException e1) {
			}
		} finally {
			// closePstmt(pstmt);
			// pstmtHead.close
			// conn.close();
		}

		return false;
	}

	// 插入订单,参数1 单头数据; 参数2 单身数据
	public boolean insertCOPTC(ArrayList<String> list,
			ArrayList<ArrayList<String>> infoList) throws SQLException {

		// static Logger log = Logger.getLogger(UpdateLog.class);
		try {
			String sqlHead = "INSERT INTO  COPTC\n"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			String sqlBody = "INSERT INTO  COPTD\n"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmtHead = null;
			PreparedStatement pstmtBody = null;
			conn.setAutoCommit(false);
			pstmtHead = conn.prepareStatement(sqlHead,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int j = 1;
			for (String ii : list) {
				// pstmtHead.setString(j, i);// creator
				// //System.out.println(i+"   长度： "+i.length());
				// j++;

				switch (j) {
				case 16:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 33:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 35:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 36:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 37:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 38:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 48:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 50:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 51:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 52:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 53:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 65:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 69:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 70:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 71:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 75:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 76:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 90:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 91:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 92:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 93:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 94:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				case 95:
					pstmtHead.setBigDecimal(j, new BigDecimal(ii).setScale(2,
							RoundingMode.HALF_UP));
					break;
				default:
					pstmtHead.setString(j, ii);
					break;
				}

				// if (logger.isDebugEnabled()) {
				//					logger.debug("|j:"+j+"|    ii: "+ii+"|    ii.length: "+ii.length()); //$NON-NLS-1$ //$NON-NLS-2$
				// }

				// if (logger.isDebugEnabled()) {
				//					logger.debug("j:"+j+"    ii: "+ii); //$NON-NLS-1$
				// System.out.println("j:"+j+"|    ii: "+ii+"|    ii.length: "+ii.length());
				//
				j++;

			}
			pstmtHead.executeUpdate();
			conn.commit();

			pstmtBody = conn.prepareStatement(sqlBody,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			j = 1;
			for (ArrayList<String> tList : infoList) {
				for (String dList : tList) {
					// pstmtBody.setString(j, dList);
					// j++;
					//

					switch (j) {
					case 15:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 16:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 18:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 19:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 29:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 31:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 32:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 33:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 37:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 38:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 39:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 40:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 41:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 42:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 44:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 45:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 46:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 47:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 48:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 49:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 50:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 51:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 52:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 57:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 58:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 59:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 61:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 62:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 63:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 64:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 65:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 66:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 74:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 75:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 76:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 77:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 78:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					case 79:
						pstmtBody.setBigDecimal(j, new BigDecimal(dList)
								.setScale(2, RoundingMode.HALF_UP));
						break;
					default:
						pstmtBody.setString(j, dList);
						break;
					}

					// if (logger.isDebugEnabled()) {
					//						logger.debug("|j:"+j+"|    ii: "+dList+"|    ii.length: "+dList.length()); //$NON-NLS-1$ //$NON-NLS-2$
					// }

					j++;

				}
				j = 1;
				pstmtBody.addBatch();
			}
			pstmtBody.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			return true;

		} catch (Exception e) {
			try {
				conn.rollback();

				// System.out.println(list);
				// 测试输出
				// for(String i:list){
				// System.out.println(i+"   长度： "+i.length());
				// }

				// for(ArrayList<String> l:infoList){
				// for(String i:infoList.get(0)){
				// System.out.println(i+"   长度： "+i.length());
				// }
				// }
				e.getCause();
				return false;
			} catch (SQLException e1) {
			}
		} finally {
			// closePstmt(pstmt);
			// pstmtHead.close
			// conn.close();
		}
		return false;

		// System.out.println(i);
	}

	// 插入订单单身
	public boolean insertCOPTD(ArrayList list) throws SQLException {

		String sql = "INSERT INTO \n"
				+ "MYNOTTA(CREATOR,CREATE_DATE,MODIFIER,MODI_DATE,FLAG,TA001,TA003,TA004,TA005,TA006,TA009,TA010,TA022,UDF01,UDF02,UDF03,UDF04,UDF05)\n"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		i = pstmt.executeUpdate();
		// System.out.println(i);

		if (i == 1) {

			stat = true;
		} else {
			stat = false;
		}

		return stat;
	}

	// 更新，撤审，审核
	public boolean updateErp(ArrayList list) throws SQLException {
		boolean stat = false;

		String sql = "UPDATE MYNOTTA SET TA022=? WHERE TA009=? AND TA001=?";
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		pstmt.setString(1, ((String) list.get(0)).trim());
		pstmt.setString(2, ((String) list.get(1)).trim());
		pstmt.setString(3, ((String) list.get(2)).trim());

		i = pstmt.executeUpdate();

		if (i == 1) {

			stat = true;
		} else {
			stat = false;
		}

		return stat;
	}

	// 修改保存
	public boolean updateMoreErp(ArrayList list) throws SQLException {
		boolean stat = false;

		String sql = "UPDATE MYNOTTA SET MODIFIER=?,MODI_DATE=?,TA004=?,TA006=?,TA003=?  WHERE TA001=? AND TA009=?";
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		pstmt.setString(1, ((String) list.get(0)).trim());
		pstmt.setString(2, ((String) list.get(1)).trim());
		pstmt.setString(3, ((String) list.get(2)).trim());
		pstmt.setString(4, ((String) list.get(3)).trim());
		pstmt.setString(5, ((String) list.get(4)).trim());
		pstmt.setString(6, ((String) list.get(5)).trim());
		pstmt.setString(7, ((String) list.get(6)).trim());

		i = pstmt.executeUpdate();

		if (i == 1) {

			stat = true;
		} else {
			stat = false;
		}

		return stat;
	}

	// 删除
	public boolean deleteErp(ArrayList list) throws SQLException {
		boolean stat = false;

		String sql = "DELETE FROM  MYNOTTA WHERE TA009=? AND TA001=?";
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		pstmt.setString(1, ((String) list.get(0)).trim());
		pstmt.setString(2, ((String) list.get(1)).trim());

		i = pstmt.executeUpdate();

		if (i == 1) {

			stat = true;
		} else {
			stat = false;
		}

		return stat;
	}

	// 删除
		public boolean deleteErp(String sql) throws SQLException {
			boolean stat = false;

			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			i = pstmt.executeUpdate();

			if (i >= 1||i==0) {

				stat = true;
			} else {
				stat = false;
			}

			return stat;
		}
		
	// 直接查询语句反回结果

	public ResultSet rsErp(String sql) throws SQLException {

		ResultSet rs = null;
		Statement stmt = null;
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery(sql);

		return rs;
	}

	// 一个参数预查询
	public ResultSet rsErp(String sql, String args1) throws SQLException {

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		pstmt.setString(1, args1);
		rs = pstmt.executeQuery();

		return rs;
	}

	// 两个参数预查询
	public ResultSet rsErp(String sql, String args1, String args2)
			throws SQLException, SQLException, SQLException, SQLException {

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		pstmt.setString(1, args1);
		pstmt.setString(2, args2);
		rs = pstmt.executeQuery();

		return rs;
	}

	// 三个参数预查询
	public ResultSet rsErp(String sql, String args1, String args2, String args3)
			throws SQLException {

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		pstmt.setString(1, args1);
		pstmt.setString(2, args2);
		pstmt.setString(3, args3);
		rs = pstmt.executeQuery();

		return rs;
	}

	// 四个参数预查询
	public ResultSet rsErp(String sql, String args1, String args2,
			String args3, String args4) throws SQLException {

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		pstmt.setString(1, args1);
		pstmt.setString(2, args2);
		pstmt.setString(3, args3);
		pstmt.setString(4, args4);
		rs = pstmt.executeQuery();

		return rs;
	}

	// 调用存储过程查询应付余额 procPurchaseSelect
	public ResultSet rsErpProc(String param1, String param2, String param3)
			throws ClassNotFoundException, SQLException {

		ResultSet rs = null;
		PreparedStatement cstmt = null;
		String sqls = "{CALL procPurchaseSelect(?,?,?)}";
		cstmt = conn.prepareCall(sqls, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		cstmt.setString(1, param1);
		cstmt.setString(2, param2);
		cstmt.setString(3, param3);
		rs = cstmt.executeQuery();
		return rs;
	}

	// 调用存储过程BOM展阶
	public ResultSet cstmtExpand(String invmb) throws ClassNotFoundException,
			SQLException {

		ResultSet rs = null;
		PreparedStatement cstmt = null;
		String sqls = "{CALL SP_BOM_EXPAND(?)}";
		cstmt = conn.prepareCall(sqls, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		cstmt.setString(1, invmb);
		rs = cstmt.executeQuery();
		return rs;
	}

	// 调用预查询 查询厂内产品工艺路线
	public String sqlBOMMF(String invmb, String bommfno) throws SQLException,
			ClassNotFoundException {
		ResultSet rsx;
		String rss = "0.0";
		String sql = "select convert(float,SUM(MF036)) from BOMMF where MF001=? AND MF002=?";
		rsx = this.rsErp(sql, invmb, bommfno);
		while (rsx.next()) {

			if (rsx.getString(1) == null) {
				rss = "0.0";
			} else {
				rss = rsx.getString(1);
			}
		}

		// this.sqlClose();
		return rss;
	}

	// //调用预查询 查询委外产品工艺路线
	// public String sqlOutBOMMF(String invmb, String bommfno) throws
	// SQLException, ClassNotFoundException {
	// ResultSet rsx;
	// String rss = "0.0";
	// String sql =
	// "select convert(float,SUM(MF018)) from BOMMF where MF001=? AND MF002=?";
	// rsx = this.rsErp(sql, invmb, bommfno);
	//
	// //取价顺位：1,委外核价单，2，产品工艺路线中的委外单价
	//
	// while (rsx.next()) {
	//
	// if (rsx.getString(1) == null)
	// rss = "0.0";
	// else
	// rss = rsx.getString(1);
	// }
	// // this.sqlClose();
	// return rss;
	// }
	// 调用预查询 查询委外核价单单价总额
	public String sqlOutBOMMF(String invmb, String bommfno)
			throws SQLException, ClassNotFoundException {
		ResultSet rsx;
		String rss = "0.0";
		Double rsssum = 0.0;

		String[] sqlxx = new String[2]; // 存放供应商编号，委外工序号

		// 取出该品号，该工艺路线编号中所有的委外的工序，工艺，供应商编号
		String sqlx = "SELECT * FROM BOMMF WHERE MF001=? AND MF002=? AND MF005='2'";
		rsx = this.rsErp(sqlx, invmb, bommfno);
		while (rsx.next()) {

			sqlxx[0] = rsx.getString("MF006"); // 委外供应商编号
			sqlxx[1] = rsx.getString("MF003");// 委外加工顺序
			rss = this.sqlOutBOMMF(invmb, bommfno, sqlxx[0], sqlxx[1]);
			rsssum = rsssum + Double.valueOf(rss);
		}

		rss = String.format("%.4f", rsssum);
		return rss;

	}

	// 调用预查询 查询委外核价单单个供应商单价 ,工艺路线信息用 out代表供应商编号,sequence代表加工顺序
	public String sqlOutBOMMF(String invmb, String bommfno, String out,
			String sequence) throws SQLException, ClassNotFoundException {
		ResultSet rsx;
		ResultSet rsy = null;
		String rss = "0.0";

		// 取得指定品号、工艺路线编号、、委外供应商、加工顺序的加工信息
		String sqlx = "SELECT * FROM BOMMF WHERE MF001=? AND MF002=? AND MF005='2' AND MF006=? AND MF003=?";

		// 取委外核价单中单价最高的一笔 --TM004=？ TN004=? TN007=? TM004 供应商编号 TN004 品号 TN007
		// 工艺
		String sqly = "select TOP 1 ISNULL(CONVERT(FLOAT,TN009),0) AS TN009,MOCTM.TM006 AS TM006 \n"
				+ "FROM MOCTN \n"
				+ "LEFT JOIN MOCTM ON TM001=TN001 AND TM002=TN002 \n"
				+ "WHERE  TM004=? AND TN004=? AND TN007=? ORDER BY TN009 DESC";

		String[] sqlxx = new String[3];
		Double sqlysum = 0.0;

		//
		rsx = this.rsErp(sqlx, invmb, bommfno, out, sequence);

		// 用if的原因是结果集只可能为一行，或者为空，在产品工艺路线单身中不可能同时存在相同加工顺序的两笔数据 ,返回不含税的值
		if (rsx.next()) {

			sqlxx[0] = rsx.getString("MF004"); // 委外加工工艺编号
			sqlxx[1] = rsx.getString("MF006"); // 供应商编号
			rsy = this.rsErp(sqly, sqlxx[1], invmb, sqlxx[0]);
			// System.out.println(rss);
			// System.out.println(sqlxx[0]+"         "+sqlxx[1]+"         "+invmb+"         "+out+"         "+sequence+rss);
			if (rsy.next()) {

				sqlxx[2] = rsy.getString("TM006"); // 是否含税 取第二个查询
				if (sqlxx[2].equals("Y")) {

					sqlysum = Double.valueOf(rsy.getString(1)) / 1.17;
					rss = String.valueOf(sqlysum);
				} else if (sqlxx[2].equals("N")) {

					sqlysum = Double.valueOf(rsy.getString(1));
					rss = String.format("%.4f", sqlysum);

				} else {
					rss = "0.0";
				}
				// System.out.println(sqlysum);
				// System.out.println(sqlxx[0]+"         "+sqlxx[1]+"         "+invmb+"         "+out+"         "+sequence+rss);
			} else {
				rss = "0.0";
			}

			/*
			 * 输出函数
			 */
			// System.out.println(invmb+"         "+bommfno+"         "+out+"         ");
			// System.out.println(sqlxx[0]+"    "+sqlxx[1]);
			// System.out.println(sqlysum);
			// System.out.println(rss);

		}

		return rss;
	}

	// else
	// rss = "0.0";
	// this.sqlClose();
	// 调用预查询 最近采购进货价， 返回不含 税的值
	public String sqlPURTH(String invmb) throws SQLException,
			ClassNotFoundException {
		ResultSet rsx;
		ResultSet rsy;
		String rsz = "0.0";
		String rss = "0.0";
		// 不含税单位进价除以1.17
		String sql = "select top 1 ISNULL(convert(float,TH018),0) AS TH018,ISNULL(TG030,0) AS TG030 from PURTH LEFT JOIN PURTG ON TG001=TH001 AND TG002=TH002 WHERE TH004=?  ORDER BY TG003 DESC";
		String sqly = "select ISNULL(convert(float,MB049),0) AS MB049,MB102 from INVMB WHERE MB001=?";
		rsx = this.rsErp(sql, invmb);
		rsy = this.rsErp(sqly, invmb);

		// 如果没有最近采购价，则取品号信息中的最近采购价

		if (rsx.next()) {

			// 如果最近进货单中存在，取最近进货单单价 并除以税率
			// System.out.println(rsx.getString("TH018")+"                  "+rsx.getString("TG030"));
			rss = String.valueOf(Double.valueOf(rsx.getString("TH018"))
					/ (Double.valueOf(rsx.getString("TG030")) + 1.0));

		} else if (rsy.next()) {

			// 最近进货单中不存在，取品号信息中的，

			if (rsy.getString("MB102").equals("Y")) {
				rss = String
						.valueOf(Double.valueOf(rsy.getString("MB049")) / 1.17);
			} else {
				rss = rsy.getString("MB049");
			}

		} else {
			rss = "0.0";
		}

		// this.sqlClose();
		return rss;
	}

	// 调用预查询 最近销售单价,
	public String sqlCOPTH(String invmb) throws SQLException,
			ClassNotFoundException {
		ResultSet rsx;
		String rss;
		String sql = "select  convert(float,TH012) from COPTH  WHERE TH004=?  ORDER BY TH002 DESC";
		rsx = this.rsErp(sql, invmb);
		if (rsx.next()) {
			rss = rsx.getString(1) + "元"; // 销售单价
		} else {
			rss = "无最近销售记录";
		}
		// this.sqlClose();
		return rss;
	}

	// 调用预查询 最近 销售对象，日期等
	public String sqlCOPTH(String invmb, int x) throws SQLException,
			ClassNotFoundException {
		ResultSet rsx;
		String rss;
		String sql = "select TG001,TG002,TG004,MA002,CONVERT(FLOAT,TH008)\n"
				+ "from COPTG\n"
				+ "left join COPTH ON TH001=TG001 AND TH002=TG002\n"
				+ "LEFT JOIN COPMA ON MA001=TG004\n" + "where TH004=? \n"
				+ "ORDER BY TH002 DESC";
		rsx = this.rsErp(sql, invmb);
		if (rsx.next()) {
			rss = rsx.getString(1) + "-" + rsx.getString(2) + rsx.getString(3)
					+ rsx.getString(4) + rsx.getString(5) + "只"; // 客户，日期，单号
		} else {
			rss = "";
		}
		// this.sqlClose();
		return rss;
	}

	// 品名转品号
	public String[] sqlNameToNo(String spec) throws SQLException {

		String invmb[] = new String[41];
		int i = 0;
		// String sql="SELECT DISTINCT TOP 40 * \n" +
		// "FROM COPTH \n" +
		// "LEFT JOIN INVMB ON MB001=TH004\n" +
		// "LEFT JOIN COPTG ON TG001=TH001 AND TG002=TH002\n" +
		// "WHERE TG023='Y' AND MB002 LIKE ?\n" +
		// "ORDER BY TG002 DESC";

		String sql = "select TOP 40 MB001\n" + "from INVMB\n"
				+ "where MB002 like ?\n" + "order by MODI_DATE ,MB025,MB002 ";
		ResultSet rs;
		rs = this.rsErp(sql, spec);
		while (rs.next()) {
			if (!rs.getString("MB001").equals("")) {

				invmb[i] = rs.getString("MB001");

			} else {
				invmb[i] = "";
			}

			i++;
		}

		return invmb;
	}

	// 规格转品号
	public String[] sqlSpecToNo(String spec) throws SQLException {

		String invmb[] = new String[41];
		int i = 0;
		String sql = "SELECT TOP 40 * FROM INVMB WHERE MB003 LIKE ? ORDER BY MB025,MB002 ";
		ResultSet rs;
		rs = this.rsErp(sql, spec);
		while (rs.next()) {
			if (!rs.getString("MB001").equals("")) {

				invmb[i] = rs.getString("MB001");

			} else {
				invmb[i] = "";
			}

			i++;
		}

		return invmb;
	}

	public void sqlClose() throws SQLException {

		conn.close();

	}

	// 重置各表,先清除后插入数据
	public void resetInvmbCheck() {
	}
}

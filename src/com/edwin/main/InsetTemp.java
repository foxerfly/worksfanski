package com.edwin.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.edwin.sql.QueryErp;

public class InsetTemp {

	protected void logicInsertINVTK() throws ClassNotFoundException,
			SQLException {

//		QueryErp qrINVML = new QueryErp();
//		QueryErp qrINVTK=new QueryErp();
//		ResultSet rsINVML=null;
//		ArrayList list=new ArrayList();
//		String sqlINVTK = "select 'fanski','DS','','20120901','','',1,'1710','201208001',?,\n" +
//				"ML001,MB002,MB003,ML005*-1 ML005,0,0,0,0,0,0,0,0,0,ML002,ML004,'','','','','N',0,\n" +
//				"'',ML003,'','','',0,0,0,'','','','','','',0,0,0,0,0,0,'','','','','','',0,0,0,0,0,0\n" +
//				"from INVML\n" +
//				"LEFT JOIN INVMB ON MB001=ML001\n" +
//				"WHERE ML001 in(select DISTINCT MB001\n" +
//				"from tempInputFormula ) AND  ML005<>0";
//		
//		String sqlINVML="select ML001,ML002,ML003,ML004,ML005*-1 ML005  \n" +
//				"from INVML\n" +
//				"WHERE ML001 in(select DISTINCT MB001\n" +
//				"from tempInputFormula ) AND  ML005<>0 ";
//		
//		Integer TK003=1;
//		String TTK003 = "";
//		
//		rsINVML=qrINVML.rsErp(sqlINVML);
//		while(rsINVML.next()) {
//			
//			
//			if (TK003.toString().trim().length() == 1) {
//
//				TTK003 = "000" + TK003.toString().trim();
//				list.add(TTK003);
//			} else if (TK003.toString().trim().length() == 2) {
//
//				TTK003 = "00" + TK003.toString();
//				list.add(TTK003);
//			} else if (TK003.toString().trim().length() == 3) {
//				TTK003 = "0" + TK003.toString();
//				list.add(TTK003);
//			}
//			 else if (TK003.toString().trim().length() == 4) {
//				 TTK003 =TK003.toString();
//					list.add(TTK003);
//				}
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			TK003++;
//		}
//
//
//		
//
//		
//		rs=qr
//		
//		while()
//		
		
		
		
		
		
	}

	protected void logicInsertSFCTA() throws ClassNotFoundException,
			SQLException {

		QueryErp qr = new QueryErp();
		QueryErp qrSFCTANO1 = new QueryErp();
		QueryErp qrInsertSFCTA = new QueryErp();
		QueryErp qrUpdate = new QueryErp();

		String sqlMOCTA = "select TA001,TA002,TA009,TA006 from MOCTA WHERE TA001='5140' AND TA002 LIKE '120906%'";
		String sqlSFCTANO1 = "select MA001,MB001,MW001,TA015 from SFCTANO1 where MB001=?";
		String sqlInsertSFCTA = "insert into SFCTA (TA001,TA002,TA003,TA004,TA006,TA010,TA005) VALUES(?,?,?,?,?,?,?)";

		String sqUPDATESFCTA1 = "UPDATE SFCTA\n"
				+ "SET TA007=MA002,TA008='20120901',TA009='20120901',TA018='RMB',TA019=0,TA033=0,TA050='Y',SFCTA.UDF12=TA010"
				+ "from SFCTA\n" + "LEFT JOIN PURMA ON MA001=TA006\n"
				+ "WHERE TA001='5140' AND TA002 LIKE '120906%' AND  TA005='2'";

		String sqlUPDATESFCTA2 = "UPDATE SFCTA\n"
				+ "SET TA007='外协工作中心',TA008='20120901',TA009='20120901',TA018='RMB',TA019=0,TA033=0,TA050='Y'\n"
				+ "from SFCTA\n"
				+ "WHERE TA001='5140' AND TA002 LIKE '120906%' AND TA005='1'";

		String TA001 = "";
		String TA002 = "";
		String TA009 = "";
		String TA006 = "";

		String MA001 = "";
		String MW001 = "";
		String TA015 = "";

		Integer TA003 = 0;
		String TTA003 = "";

		ArrayList list = new ArrayList();
		ResultSet rs = null;
		ResultSet rsSFCTANO1 = null;
		// ResultSet rs=null;

		rs = qr.rsErp(sqlMOCTA);

		while (rs.next()) {

			TA001 = rs.getString("TA001");
			TA002 = rs.getString("TA002");
			TA009 = rs.getString("TA009");
			TA006 = rs.getString("TA006");

			rsSFCTANO1 = qrSFCTANO1.rsErp(sqlSFCTANO1, TA006);
			while (rsSFCTANO1.next()) {

				list.add(TA001);
				list.add(TA002);

				TA003 = TA003 + 10;

				if (TA003.toString().trim().length() == 2) {

					TTA003 = "00" + TA003.toString();
					list.add(TTA003);
				} else if (TA003.toString().trim().length() == 3) {
					TTA003 = "0" + TA003.toString();
					list.add(TTA003);

				}
				// System.out.println(TTA003);

				list.add(rsSFCTANO1.getString("MW001"));
				list.add(rsSFCTANO1.getString("MA001"));
				list.add(rsSFCTANO1.getString("TA015"));
				list.add("2");

				System.out.println(list);

				qrSFCTANO1.insertErp(sqlInsertSFCTA, list);

				list.removeAll(list);

				// 添加待委外工序
				list.add(TA001);
				list.add(TA002);

				TA003 = TA003 + 10;

				if (TA003.toString().trim().length() == 2) {

					TTA003 = "00" + TA003.toString();
					list.add(TTA003);
				} else if (TA003.toString().trim().length() == 3) {
					TTA003 = "0" + TA003.toString();
					list.add(TTA003);

				}
				// System.out.println(TTA003);
				list.add("777"); // 待委外工序
				list.add("81102"); // 委外工作中心
				list.add("0");
				list.add("1");

				System.out.println(list);

				qrSFCTANO1.insertErp(sqlInsertSFCTA, list);

				list.removeAll(list);

			}

			TA003 = 0;
			TTA003 = "";

		}

		// qrUpdate.rsErpUpdate(sqUPDATESFCTA1);
		// qrUpdate.rsErpUpdate(sqlUPDATESFCTA2);

	}

	protected void lgoicInsertMOCTB() throws ClassNotFoundException,
			SQLException {

		QueryErp qr = new QueryErp();
		String sql = "INSERT INTO MOCTB \n"
				+ "SELECT 'fanski','DS','','20120901','','',1, \n"
				+ "A.TA001,A.TA002,A.TB003, \n"
				+ "CAST((CAST(A.TA015 AS NUMERIC(18,4))*CAST(A.MMD AS NUMERIC(18,4))) AS NUMERIC(18,4)),0,'****',MB004,'',MB017 \n"
				+ ",'','1',MB002,MB003,A.TA006,MOCTA.TA009,'','','Y',0,0,'','','','', \n"
				+ "CAST((CAST(A.TA015 AS NUMERIC(18,4))*CAST(A.MMD AS NUMERIC(18,4))) AS NUMERIC(18,4)),0, \n"
				+ "CAST((CAST(A.TA015 AS NUMERIC(18,4))*CAST(A.MMD AS NUMERIC(18,4))) AS NUMERIC(18,4)) \n"
				+ ",'','','',0,'','','','','',CAST((CAST(A.TA015 AS NUMERIC(18,4))*CAST(A.MMD AS NUMERIC(18,4))) AS NUMERIC(18,4)), \n"
				+ "0,'2','','','','',0,MB110,MB009,'','','','',0,0,0,0,0,0,'EDWIN20120905','','','','','',0,0,0,0,0,0 \n"
				+ "FROM TEMPMOCTA1 AS A \n"
				+ "LEFT JOIN MOCTA ON MOCTA.TA001=A.TA001 AND MOCTA.TA002=A.TA002 \n"
				+ "LEFT JOIN INVMB ON A.TB003=MB001";

		if (qr.insertErp(sql)) {
			System.out.println("插入数据成功");
		} else {
			System.out.println("插入数据失败");
		}

	}

	protected void logoicInsert() throws ClassNotFoundException, SQLException {
		QueryErp qrMOCTA1 = new QueryErp();
		QueryErp qrMOCTA2 = new QueryErp();
		QueryErp qrBOMMD = new QueryErp();
		QueryErp qr = new QueryErp();

		String sqlMOCTA1 = "SELECT DISTINCT TA001,TA002,TA006,CAST(TA015 AS VARCHAR(30)) TA015 FROM MOCTA \n"
				+ "WHERE TA001 IN ('5120') AND TA011 IN('1') AND TA013='Y' AND TA006 LIKE 'A%' ORDER BY TA001,TA002";

		String sqlMOCTA2 = "SELECT DISTINCT TA001,TA002,TA006 FROM MOCTA \n"
				+ "WHERE TA001 IN ('5120') AND TA011 IN('1') AND TA013='Y' AND TA006 =? ORDER BY TA001,TA002";

		String sqlBOMMD = "SELECT MD001,MD003,CAST(CAST(MD006/MD007 AS NUMERIC(18,4)) AS VARCHAR(30)) MMD FROM BOMMD \n"
				+ "WHERE MD003 LIKE 'H%'  AND MD001=? AND MD003 NOT IN (SELECT DISTINCT TB003 FROM MOCTA  \n"
				+ "LEFT JOIN MOCTB ON TB001=TA001 AND TB002=TA002 \n"
				+ "WHERE TA001 ='5120' AND TA002=? AND TA011 IN('1') AND TA013='Y') ";

		String sqlInsert = "INSERT INTO TEMPMOCTA1 VALUES(?,?,?,?,?,?)";

		String sqlDelete = "DELETE FROM TEMPMOCTA1";

		String tempTA001 = "";
		String tempTA002 = "";
		String tempTA006 = "";
		String tempTA015 = "";
		String tempTB003 = "";

		ArrayList list = new ArrayList();

		ResultSet rsMOCTA1 = null;
		ResultSet rsMOCTA2 = null;
		ResultSet rsBOMMD = null;

		if (qr.deleteErp(sqlDelete)) {
			System.out.println("成功清空表TEMPMOCTA1");
		} else {
			System.out.println("清空失败");
		}

		rsMOCTA1 = qrMOCTA1.rsErp(sqlMOCTA1);
		while (rsMOCTA1.next()) {

			tempTA001 = rsMOCTA1.getString("TA001");
			tempTA002 = rsMOCTA1.getString("TA002");
			tempTA006 = rsMOCTA1.getString("TA006");
			tempTA015 = rsMOCTA1.getString("TA015");

			rsBOMMD = qrBOMMD.rsErp(sqlBOMMD, tempTA006, tempTA002);
			while (rsBOMMD.next()) {

				tempTB003 = rsBOMMD.getString("MD003");
				list.add(tempTA001);
				list.add(tempTA002);
				list.add(tempTA006);
				list.add(tempTA015);
				list.add(tempTB003);

				list.add(rsBOMMD.getString("MMD"));

				if (qr.insertErp(sqlInsert, list)) {
					System.out.println(list);
				} else {
					System.out.println("导入失败");
					break;
				}
				list.removeAll(list);

			}

		}

		System.out.println("查找空缺BOM结束");
		rsBOMMD.close();
		rsMOCTA1.close();
		// rsMOCTA2 .close();
		qrMOCTA1.connErp().close();
		qrMOCTA2.connErp().close();
		qrBOMMD.connErp().close();
		qr.connErp().close();
	}

}

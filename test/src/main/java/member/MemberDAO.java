package member;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import board.BoardVO;
import util.JDBCUtil;

public class MemberDAO {
	Connection conn;
	PreparedStatement pstmt;
	final String sql_selectAll = "SELECT * FROM MEMBER";
	final String sql_selectAll_F = "SELECT * FROM (SELECT * FROM MEMBER ORDER BY ROWNUM DESC) WHERE ROWNUM <=3";
	final String sql_selectOne="SELECT * FROM MEMBER WHERE MID=? AND MPW=?";
	final String sql_update = "UPDATE MEMBER SET MPW=?,MNAME=? WHERE MID=?";
	final String sql_insert="INSERT INTO MEMBER VALUES(?,?,?)";
	final String sql_delete="DELETE FROM MEMBER WHERE MID=? AND MPW=?";
	
	final String sql_bCnt="SELECT COUNT(*) AS BCNT FROM BOARD WHERE MID=?";
	final String sql_rCnt="SELECT COUNT(*) AS RCNT FROM REPLY WHERE MID=?";
	
	public ArrayList<MemberVO> selectAll(MemberVO mvo) { // 유지보수 용이
		conn=JDBCUtil.connect();
		ArrayList<MemberVO> datas = new ArrayList<MemberVO>();
		try {
			pstmt = conn.prepareStatement(sql_selectAll);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberVO MemberVO = new MemberVO();
				MemberVO.setMid(rs.getString("MID"));
				MemberVO.setMpw(rs.getString("MPW"));
				MemberVO.setMname(rs.getString("MNAME"));
				datas.add(MemberVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return datas;
	}
	
	public ArrayList<MemberVO> selectAll_F(MemberVO mvo) { // 유지보수 용이
		conn=JDBCUtil.connect();
		ArrayList<MemberVO> datas = new ArrayList<MemberVO>();
		try {
			pstmt = conn.prepareStatement(sql_selectAll_F);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberVO MemberVO = new MemberVO();
				MemberVO.setMid(rs.getString("MID"));
				MemberVO.setMpw(rs.getString("MPW"));
				MemberVO.setMname(rs.getString("MNAME"));
				datas.add(MemberVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return datas;
	}
	
	public MemberVO selectOne(MemberVO mvo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_selectOne);
			pstmt.setString(1, mvo.getMid());
			pstmt.setString(2, mvo.getMpw());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				MemberVO data=new MemberVO();
				data.setMid(rs.getString("MID"));
				data.setMname(rs.getString("MNAME"));
				data.setMpw(rs.getString("MPW"));
				return data;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return null;
	}
	
	public boolean update(MemberVO mvo) {
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_update);
			pstmt.setString(1, mvo.getMpw());
			pstmt.setString(2, mvo.getMname());
			pstmt.setString(3, mvo.getMid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}
	
	public boolean insert(MemberVO mvo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_insert);
			pstmt.setString(1, mvo.getMid());
			pstmt.setString(2, mvo.getMpw());
			pstmt.setString(3, mvo.getMname());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}
	
	public boolean delete(MemberVO mvo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_delete);
			pstmt.setString(1, mvo.getMid());
			pstmt.setString(2, mvo.getMpw());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}
	
	public boolean deleteMSample(MemberVO mvo) { // 유지보수 용이
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_bCnt);
			pstmt.setString(1, mvo.getMid());
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {			
				int bcnt = rs.getInt("BCNT");
			//	System.out.println(bcnt); 게시글 개수
				if(bcnt>0) {
					System.out.println("로그: 게시글 존재");
					return false;
				}
				else {
					pstmt=conn.prepareStatement(sql_rCnt);
					pstmt.setString(1, mvo.getMid());
					ResultSet rs2 = pstmt.executeQuery();
			//		System.out.println(rs2); 
					if(rs2.next()) {
						int rcnt = rs2.getInt("RCNT");
			//			System.out.println(rcnt); 댓글 개수
						if(rcnt>0) {
							System.out.println("로그: 댓글 존재");
							return false;
						}
						return true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return false;
	}
}

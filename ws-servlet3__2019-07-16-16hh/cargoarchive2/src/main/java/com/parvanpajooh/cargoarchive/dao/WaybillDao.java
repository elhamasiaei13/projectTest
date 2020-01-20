package com.parvanpajooh.cargoarchive.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.parvanpajooh.cargoarchive.model.Waybill;

public class WaybillDao {

	static final Logger LOG = LoggerFactory.getLogger(WaybillDao.class);

	JdbcTemplate template;
	
	
	
	/**
	 * 
	 * @param template
	 */
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	/**
	 * 
	 * @param waybill
	 * @throws Exception
	 */
	public void save(Waybill waybill) throws Exception {

		try {
			String description = waybill.getDescription();
			String waybillNumber = waybill.getWaybillNumber();
			String originalFileName = waybill.getOriginalFileName();
			String relFilePathName = waybill.getRelFilePathName();
			LocalDateTime createDate = waybill.getCreateDate();
			DateTimeFormatter formatterCreateDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String createDateStr = createDate.format(formatterCreateDate);
			Long createUserId = waybill.getCreateUserId();

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO waybill_tbl (");
			sb.append("description, waybillNumber, originalFileName, relFilePathName, createDate, createUserId )");
			sb.append("VALUES(");
			sb.append(description == null ? null : "'" + description + "'");
			sb.append(",");
			sb.append(waybillNumber == null ? null : "'" + waybillNumber + "'");
			sb.append(",");
			sb.append(originalFileName == null ? null : "'" + originalFileName + "'");
			sb.append(",");
			sb.append(relFilePathName == null ? null : "'" + relFilePathName + "'");
			sb.append(",");
			sb.append("'" + createDateStr + "'");
			sb.append(",");
			sb.append(createUserId == null ? null : createUserId);
			sb.append(")");

			String sql = sb.toString();

			LOG.debug("savequery [{}]", sql);

			template.update(sql);

		} catch (Exception e) {
			throw new Exception("Error occurred while save", e);
		}
	}

	/**
	 * 
	 * @param term
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<Waybill> findBy(String term, int page, int size, String sort) throws Exception {
		List<Waybill> list;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT * FROM waybill_tbl ");

			if (term != null) {
				sb.append(" WHERE ");
				sb.append(" waybillNumber LIKE '%");
				sb.append(term);
				sb.append("%' ");
				sb.append(" OR ");
				sb.append(" fileName LIKE '%");
				sb.append(term);
				sb.append("%' ");

			}

			if (sort == null) {
				sb.append(" ORDER BY id DESC ");
			} else {
				sb.append(" ORDER BY id ");
				sb.append(sort);
			}

			sb.append(" LIMIT ");
			sb.append(page);
			sb.append(",");
			sb.append(size);

			String query = sb.toString();

			LOG.debug("query [{}]", query);

			list = template.query(query, new RowMapper<Waybill>() {
				public Waybill mapRow(ResultSet rs, int row) throws SQLException {
					Waybill wb = new Waybill();
					wb.setId(rs.getLong("id"));// id
					wb.setWaybillNumber(rs.getString("waybillNumber"));// waybillNumber
					wb.setOriginalFileName(rs.getString("originalFileName"));// filename

					Timestamp createSqlDate = rs.getTimestamp("createDate");
					Instant instant = createSqlDate.toInstant();
					ZoneId defaultZoneId = ZoneId.systemDefault();
					LocalDateTime createLocalDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
					wb.setCreateDate(createLocalDateTime);
					Timestamp updateSqlDate = rs.getTimestamp("updateDate");

					if (updateSqlDate != null) {
					Instant instantupdate = updateSqlDate.toInstant();
					LocalDateTime updateLocalDateTime = instantupdate.atZone(defaultZoneId).toLocalDateTime();
					wb.setUpdateDate(updateLocalDateTime);
					}

					Long createUserId = DaoTools.getLong(rs, "createUserId");
					if (createUserId != null) {
						wb.setCreateUserId(createUserId);
					}

					Long updateUserId = DaoTools.getLong(rs, "updateUserId");
					if (updateUserId != null) {
						wb.setUpdateUserId(updateUserId);
					}

					wb.setDescription(rs.getString("description"));
					wb.setRelFilePathName(rs.getString("relFilePathName"));
					return wb;
				}
			});

		} catch (Exception e) {
			throw new Exception("Error occurred while findBy", e);
		}

		return list;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */

	public int delete(long id) {
		String sql = "DELETE FROM waybill_tbl WHERE id=" + id + "";
		return template.update(sql);

	}

	/**
	 * 
	 * @param id
	 * @return
	 */

	public Waybill get(long id) {
		String sql = "SELECT * FROM waybill_tbl WHERE id=?";
		return template.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Waybill>(Waybill.class));
	}

	/**
	 * 
	 * @param waybill
	 * @return
	 * @return
	 * @throws Exception
	 */
	public int update(Waybill waybill) throws Exception {
		int result = 0;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE waybill_tbl SET  description='");
			sb.append(waybill.getDescription());
			sb.append("' , ");
			sb.append(" updateUserID='");
			sb.append(waybill.getUpdateUserId());
			sb.append("', relFilePathName='");
			sb.append(waybill.getRelFilePathName());
			sb.append("', createDate='");
			sb.append(waybill.getCreateDate());
			sb.append("',updateDate='");
			sb.append(waybill.getUpdateDate());
			sb.append("',createUserId='");
			sb.append(waybill.getCreateUserId());
			sb.append("', updateUserId='");
			sb.append(waybill.getUpdateUserId());
			sb.append("',wayBillNumber=' ");
			sb.append(waybill.getWaybillNumber());
			sb.append("',originalFileName='");
			sb.append(waybill.getOriginalFileName());
			sb.append("'");
			sb.append("WHERE id=");
			sb.append(waybill.getId());

			String query = sb.toString();

			LOG.debug("queryupdate [{}]", query);
			String sql = "UPDATE waybill_tbl SET  description='" + waybill.getDescription() + "', updateDate='"
					+ waybill.getUpdateDate() + "' ,updateUserID='" + waybill.getUpdateUserId() + "', relFilePathName='"
					+ waybill.getRelFilePathName() + "', wayBillNumber='" + waybill.getWaybillNumber()
					+ "', originalFileName='" + waybill.getOriginalFileName() + "'where id=" + waybill.getId() + "";

			result = template.update(query);
		} catch (Exception e) {
			throw new Exception("Error occurred while update", e);
		}
		return result;
	}
}

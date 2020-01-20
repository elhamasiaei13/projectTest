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

import com.parvanpajooh.cargoarchive.model.Manifest;
import com.parvanpajooh.cargoarchive.model.Waybill;

public class ManifestDao {

	static final Logger LOG = LoggerFactory.getLogger(ManifestDao.class);

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
	 * @param manifest
	 * @throws Exception
	 */
	public void save(Manifest manifest) throws Exception {

		try {
			String description = manifest.getDescription();
			String manifestNumber = manifest.getManifestNumber();
			String originalFileName = manifest.getOriginalFileName();
			String relFilePathName = manifest.getRelFilePathName();
			LocalDateTime createDate = manifest.getCreateDate();
			DateTimeFormatter formatterCreateDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String createDateStr = createDate.format(formatterCreateDate);
			Long createUserId = manifest.getCreateUserId();

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO manifest_tbl (");
			sb.append("description, manifestNumber, originalFileName, relFilePathName, createDate, createUserId )");
			sb.append("VALUES(");
			sb.append(description == null ? null : "'" + description + "'");
			sb.append(",");
			sb.append(manifestNumber == null ? null : "'" + manifestNumber + "'");
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
	public List<Manifest> findBy(String term, int page, int size, String sort) throws Exception {
		List<Manifest> list;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT * FROM manifest_tbl ");

			if (term != null) {
				sb.append(" WHERE ");
				sb.append(" manifestNumber LIKE '%");
				sb.append(term);
				sb.append("%' ");
				sb.append(" OR ");
				sb.append(" originalFileName LIKE '%");
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

			list = template.query(query, new RowMapper<Manifest>() {
				public Manifest mapRow(ResultSet rs, int row) throws SQLException {
					Manifest mf = new Manifest();
					mf.setId(rs.getLong("id"));// id
					mf.setManifestNumber(rs.getString("manifestNumber"));// manifestNumber
					mf.setOriginalFileName(rs.getString("originalFileName"));// filename

					Timestamp createSqlDate = rs.getTimestamp("createDate");
					Instant instant = createSqlDate.toInstant();
					ZoneId defaultZoneId = ZoneId.systemDefault();
					LocalDateTime createLocalDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
					mf.setCreateDate(createLocalDateTime);
					Timestamp updateSqlDate = rs.getTimestamp("updateDate");

					if (updateSqlDate != null) {
					Instant instantupdate = updateSqlDate.toInstant();
					LocalDateTime updateLocalDateTime = instantupdate.atZone(defaultZoneId).toLocalDateTime();
					mf.setUpdateDate(updateLocalDateTime);
					}

					Long createUserId = DaoTools.getLong(rs, "createUserId");
					if (createUserId != null) {
						mf.setCreateUserId(createUserId);
					}

					Long updateUserId = DaoTools.getLong(rs, "updateUserId");
					if (updateUserId != null) {
						mf.setUpdateUserId(updateUserId);
					}

					mf.setDescription(rs.getString("description"));
					mf.setRelFilePathName(rs.getString("relFilePathName"));
					return mf;
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
		String sql = "DELETE FROM manifest_tbl WHERE id=" + id + "";
		return template.update(sql);

	}

	/**
	 * 
	 * @param id
	 * @return
	 */

	public Manifest get(long id) {
		String sql = "SELECT * FROM manifest_tbl WHERE id=?";
		return template.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Manifest>(Manifest.class));
	}

	/**
	 * 
	 * @param Manifest
	 * @return
	 * @return
	 * @throws Exception
	 */
	public int update(Manifest manifest) throws Exception {
		int result = 0;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE manifest_tbl SET  description='");
			sb.append(manifest.getDescription());
			sb.append("' , ");
			sb.append(" updateUserID='");
			sb.append(manifest.getUpdateUserId());
			sb.append("', relFilePathName='");
			sb.append(manifest.getRelFilePathName());
			sb.append("', createDate='");
			sb.append(manifest.getCreateDate());
			sb.append("',updateDate='");
			sb.append(manifest.getUpdateDate());
			sb.append("',createUserId='");
			sb.append(manifest.getCreateUserId());
			sb.append("', updateUserId='");
			sb.append(manifest.getUpdateUserId());
			sb.append("',manifestNumber=' ");
			sb.append(manifest.getManifestNumber());
			sb.append("',originalFileName='");
			sb.append(manifest.getOriginalFileName());
			sb.append("'");
			sb.append("WHERE id=");
			sb.append(manifest.getId());
			String query = sb.toString();
			LOG.debug("queryupdate [{}]", query);
			String sql = "UPDATE manifest_tbl SET  description='" + manifest.getDescription() + "', updateDate='"
					+ manifest.getUpdateDate() + "' ,updateUserID='" + manifest.getUpdateUserId() + "', relFilePathName='"
					+ manifest.getRelFilePathName() + "', manifestNumber='" + manifest.getManifestNumber()
					+ "', originalFileName='" + manifest.getOriginalFileName() + "'where id=" + manifest.getId() + "";

			result = template.update(query);
		} catch (Exception e) {
			throw new Exception("Error occurred while update", e);
		}
		return result;
	}
	
}

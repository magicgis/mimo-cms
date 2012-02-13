package com.mimo.cms.infrastruture.persist;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.mimo.cms.domain.article.ArticleMiningTask.TaskStatus;

/**
 * 
 * @author loudyn
 * 
 */
public class MybatisArticleMiningTaskStatusTypeHandler extends BaseTypeHandler<TaskStatus> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int index, TaskStatus parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(index, parameter.toString());
	}

	@Override
	public TaskStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String status = rs.getString(columnName);
		return Enum.valueOf(TaskStatus.class, status);
	}

	@Override
	public TaskStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String status = cs.getString(columnIndex);
		return Enum.valueOf(TaskStatus.class, status);
	}
}

package com.scholanova.projectstore.repositories;

import com.scholanova.projectstore.exceptions.ModelNotFoundException;
import com.scholanova.projectstore.models.Stocks;
import com.scholanova.projectstore.models.Store;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StocksRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public StocksRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Stocks create(Stocks stocksToCreate, int idStore) {
		KeyHolder holder = new GeneratedKeyHolder();

		String query = "INSERT INTO STOCKS " +
				"(NAME, TYPE, VALUE, IDSTORE) VALUES " +
				"(:name, :type, :value, :idStore)";

		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("name", stocksToCreate.getName())
				.addValue("type", stocksToCreate.getType())
				.addValue("value", stocksToCreate.getValue())
				.addValue("idStore", idStore);

		jdbcTemplate.update(query, parameters, holder);

		Integer newlyCreatedId = (Integer) holder.getKeys().get("ID");
		try {
			return this.getById(newlyCreatedId);
		} catch (ModelNotFoundException e) {
			return null;
		}
	}

	public Stocks getById(Integer id) throws ModelNotFoundException {
		String query = "SELECT ID as id, " +
				"NAME AS name, " +
				"TYPE AS type, " +
				"VALUE AS value, " +
				"IDSTORE AS idStore " +
				"FROM STOCKS " +
				"WHERE ID = :id";

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);

		return jdbcTemplate.query(query,
				parameters,
				new BeanPropertyRowMapper<>(Stocks.class))
				.stream()
				.findFirst()
				.orElseThrow(ModelNotFoundException::new);
	}
}

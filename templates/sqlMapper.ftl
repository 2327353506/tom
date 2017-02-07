<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${package}.${tableName}Mapper" >

	<!-- 查询所有 -->
	<select id="findAll"  resultType="map">
		select 
			${column}
		from ${tableName}
	</select>

	<!-- 通过id查询所有 -->
	<select id="findById"  resultType="map">
		select 
			${column}
		from ${tableName}
		where id = ${"#"}{id}
	</select>

	<!-- 保存数据 -->
	<insert id="insert" useGeneratedKeys="true" keyProperty="id">
		 insert into 
			${tableName}
			<trim prefix="(" suffix=")values " suffixOverrides=",">
					<if test="${column} != null">
						${column},
					</if>
			</trim>
			<trim prefix="(" suffix=") " suffixOverrides=",">
					<if test="${column} != null">
						${"#"}{${column}},
					</if>
			</trim>
	</insert>

	<!-- 更新数据 -->
	<update id="update">
		update 
			${tableName}
		<set >
			<if test="${column} != null">
				${column} = ${"#"}{${column}}
			</if>
		</set>
		where id = ${"#"}{id}
	<update>

</mapper>


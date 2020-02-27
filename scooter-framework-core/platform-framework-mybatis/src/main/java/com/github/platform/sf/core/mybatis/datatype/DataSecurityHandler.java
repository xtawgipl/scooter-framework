package com.github.platform.sf.core.mybatis.datatype;

import com.github.platform.sf.common.util.cache.GuavaCache;
import com.github.platform.sf.common.util.security.std.DESEncryptUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 加解密自定义类型转换
 *
 * @author zhangjj
 * @create 2019-08-14 9:14
 **/
//@MappedTypes(String.class)
//@MappedJdbcTypes(JdbcType.VARCHAR)
public class DataSecurityHandler extends BaseTypeHandler<String> {

    private static final String KEY = "cipherKey";

    private static final GuavaCache<String, String> ENCRYPT_CACHE = new GuavaCache<>(1000*5L, 10000L);

    private static final GuavaCache<String, String> DECRYPT_CACHE = new GuavaCache<>(1000*5L, 10000L);

    private final String cipherKey = System.getProperty(KEY);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        String val = StringUtils.trimWhitespace(parameter);
        if(val == null){
            ps.setString(i, null);
        }
        String cipherText = ENCRYPT_CACHE.get(val);
        if(cipherText == null){
            cipherText = DESEncryptUtil.encrypt(val, cipherKey);
        }
        ps.setString(i, cipherText);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String val = StringUtils.trimWhitespace(rs.getString(columnName));
        return getText(val);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String val = StringUtils.trimWhitespace(rs.getString(columnIndex));
        return getText(val);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String val = StringUtils.trimWhitespace(cs.getString(columnIndex));
        return getText(val);
    }

    private String getText(String ciphertext){
        if(ciphertext == null){
            return null;
        }
        String planText = DECRYPT_CACHE.get(ciphertext);
        if(planText == null){
            planText = DESEncryptUtil.decrypt(ciphertext, cipherKey);
        }
        return planText;
    }

}

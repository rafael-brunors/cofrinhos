package com.cofrinhos.colecao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

public class ColecaoTotalPorDiaResultTransformer implements ResultTransformer {

	private static final long serialVersionUID = 4387880360457288444L;

	@SuppressWarnings("rawtypes")
	@Override
    public List transformList(List list) {
        return list;
    }

	@Override
	public Object transformTuple(Object[] objects, String[] strings) {
		ColecaoTotalPorDia result = new ColecaoTotalPorDia();

		for (int i = 0; i < objects.length; i++) {
		    setField(result, strings[i], objects[i]);
		}
		
		return result;
	}
	
	 private void setField(ColecaoTotalPorDia result, String string, Object object) {

        if (string.equalsIgnoreCase(ColecaoTotalPorDia.Fields.DATA.toString())) {
            result.setData((Date) object);
        } else if (string.equalsIgnoreCase(ColecaoTotalPorDia.Fields.VALOR.toString())) {
            result.setValor((BigDecimal) object);
        } else {
            throw new RuntimeException("unknown field");
        }
    }
}

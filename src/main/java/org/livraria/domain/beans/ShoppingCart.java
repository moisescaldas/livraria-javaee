package org.livraria.domain.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class ShoppingCart implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6649437876057352447L;

	private Map<ShoppingItem, Integer> items = new LinkedHashMap<>();

	public void add(ShoppingItem item) {
		items.put(item, getQuantity(item) + 1);
	}

	public Integer getQuantity(ShoppingItem item) {
		if (!items.containsKey(item)) {
			return 0;
		}

		return items.get(item);
	}

	public Integer getQuantity() {
		return items.values().stream().reduce(0, (next, accumulator) -> next + accumulator);
	}

	public List<ShoppingItem> getList() {
		return items.keySet().stream().collect(Collectors.toList());
	}

	public BigDecimal getTotal(ShoppingItem item) {
		return item.getTotal(getQuantity(item));
	}

	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;

		items.keySet().stream().map(item -> item.getTotal(getQuantity(item))).forEach(total::add);
		
		return total;
	}
	
	public void remove(ShoppingItem item) {
		items.remove(item);
	}
	
	public Boolean isEmpty() {
		return items.isEmpty();
	}
}

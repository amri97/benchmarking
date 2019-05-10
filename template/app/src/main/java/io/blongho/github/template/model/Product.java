package io.blongho.github.template.model;

/**
 * @author  Bernard Che Longho
 * The type Product<br>
 *     A product has a name and a description
 * @since 2019-05-10
 * @version 1.0
 */
public class Product {
	private long product_id;
	private String name;
	private String description;

	/**
	 * Instantiates a new Product.
	 */
	public Product() {
	}

	/**
	 * Instantiates a new Product.
	 *
	 * @param product_id  the product id
	 * @param name        the name
	 * @param description the description
	 */
	public Product(final long product_id, final String name, final String description) {
		this.product_id = product_id;
		this.name = name;
		this.description = description;
	}

	/**
	 * Gets product id.
	 *
	 * @return the product id
	 */
	public long getProduct_id() {
		return product_id;
	}

	/**
	 * Sets product id.
	 *
	 * @param product_id the product id
	 */
	public void setProduct_id(final long product_id) {
		this.product_id = product_id;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description.
	 *
	 * @param description the description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Product{");
		sb.append("product_id=").append(product_id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", description='").append(description).append('\'');
		sb.append('}');
		return sb.toString();
	}

}

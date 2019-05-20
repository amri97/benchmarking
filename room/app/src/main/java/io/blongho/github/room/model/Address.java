/*
 * MIT License
 *
 * Copyright (c) 2019 Bernard Che Longho
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.blongho.github.room.model;

/**
 * An Address knows the street, city, postalcode and country
 * @author Bernard Che Longho
 * @since 2019-05-10
 * @version 1.0
 */
public class Address {
	private String street;
	private String city;
	private String postcode;
	private String country;

	/**
	 * Instantiates a new Address.
	 */
	public Address() {
	}

	/**
	 * Instantiates a new Address.
	 *
	 * @param street   the street
	 * @param city     the city
	 * @param postcode the postcode
	 * @param country  the country
	 */
	public Address(final String street, final String city, final String postcode, final String country) {
		this.street = street;
		this.city = city;
		this.postcode = postcode;
		this.country = country;
	}

	/**
	 * Gets street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets street.
	 *
	 * @param street the street
	 */
	public void setStreet(final String street) {
		this.street = street;
	}

	/**
	 * Gets city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets city.
	 *
	 * @param city the city
	 */
	public void setCity(final String city) {
		this.city = city;
	}

	/**
	 * Gets postcode.
	 *
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * Sets postcode.
	 *
	 * @param postcode the postcode
	 */
	public void setPostcode(final String postcode) {
		this.postcode = postcode;
	}

	/**
	 * Gets country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets country.
	 *
	 * @param country the country
	 */
	public void setCountry(final String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Address{");
		sb.append("street='").append(street).append('\'');
		sb.append(", city='").append(city).append('\'');
		sb.append(", postcode='").append(postcode).append('\'');
		sb.append(", country='").append(country).append('\'');
		sb.append('}');
		return sb.toString();
	}
}

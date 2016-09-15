package com.klutzer.wineshop.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Currency;

/**
 * Classe criada para adotar o design-pattern Money.<br>
 * Este padrão é importante para cálculos monetários, uma vez que usar tipos double
 * ou até mesmo BigDecimal para dinheiro não é uma boa prática e pode gerar problemas
 * de arredondamento
 * @author erico
 *
 */
public final class Money implements Serializable, Cloneable, Comparable<Money> {

	private static final long serialVersionUID = 1813100511274538648L;
	
	private long value;
	private Currency currency;

	// métodos de criação
	public static final Money ZERO = new Money(BigDecimal.ZERO); 
	public static final Money ONE = new Money(BigDecimal.ONE); 
	
	public static Money valueOf(String value, String currencyIsoCode){
		return valueOf(new BigDecimal(value), currencyIsoCode);
	}
	
	public static Money valueOf(Double value) {
		return valueOf(BigDecimal.valueOf(value));
	}

	public static Money valueOf(BigDecimal value) {
		return valueOf(value, Currency.getInstance("BRL"));
	}
	
	public Money(BigDecimal value) {
		this.currency = Currency.getInstance("BRL");
		this.value = toLongRepresentation(value, currency);
	}
	
	public static Money valueOf(String value) {
		return valueOf(new BigDecimal(value), Currency.getInstance("BRL"));
	}

	public static Money valueOf(BigDecimal value, String currencyIsoCode){
		return valueOf(value, Currency.getInstance(currencyIsoCode));
	}

	public static Money valueOf(BigDecimal value, Currency currency){
		return new Money(toLongRepresentation(value,currency),currency);
	}

	private static long toLongRepresentation(BigDecimal value,Currency currency){
		return value.movePointRight(currency.getDefaultFractionDigits()).longValue();
	}

	private static BigDecimal fromLongRepresentation(long amount,Currency currency){
		BigDecimal value = new BigDecimal(amount);
		return value.movePointLeft(currency.getDefaultFractionDigits());
	}

	private Money (long value, Currency currency){
		this.value = value;
		this.currency = currency;
	}

	public BigDecimal getAmount(){
		return fromLongRepresentation(value, currency);
	}

	public Currency getCurrency(){
		return currency;
	}

	public Money plus(Money other){
		if (!other.currency.equals(this.currency)){
			throw new IllegalArgumentException("currencies doesn't match");
		}

		// é a mesma moeda. Money é imutável. Cria outro com novo valor
		return new Money(this.value + other.value, this.currency);

	}

	public Money subtract(Money other){
		if (!other.currency.equals(this.currency)){
			throw new IllegalArgumentException("currencies doesn't match");
		}

		// é a mesma moeda. Money é imutável. Cria outro com novo valor
		return new Money(this.value - other.value, this.currency);

	}

	public Money multiply (Number factor){
		BigDecimal bigFactor;

		if (factor instanceof BigDecimal){
			bigFactor = (BigDecimal)factor;
		} else {
			bigFactor = new BigDecimal(factor.toString());
		}

		long result = bigFactor.multiply(new BigDecimal(this.value)).longValue();

		return new Money(result, currency);
	}

	public Money[] distribute (int n){

		BigInteger bigValue =  BigInteger.valueOf(this.value);
		BigInteger[] result = bigValue.divideAndRemainder(BigInteger.valueOf(n));

		Money[] distribution = new Money[n];

		// todos os valores são iguais
		Arrays.fill(distribution, new Money(result[0].longValue(), currency));

		// adiciona o resto no primeiro
		// substituindo o valor atual nessa posição
		distribution[distribution.length-1] = distribution[0].plus(
				new Money(result[1].longValue(),this.currency));

		return distribution;
	}
	
	@Override
	public String toString() {

		return new DecimalFormat("###,##0.00").format(getAmount().doubleValue());
	}
	
	/**
	 * Retorna o símbolo da moeda juntamente com o valor formatado
	 * em 2 casas decimais para os centavos e pontos separando os milhares
	 */
	public String toFormattedString() {
		
		return new DecimalFormat(String.format("'%s '###,##0.00", currency.getSymbol())).format(getAmount().doubleValue());
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Money) {
			Money money = (Money) obj;
			
			return money.getAmount().equals(getAmount()) && 
					money.getCurrency().equals(currency);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {

		return (currency.getCurrencyCode()+
				String.valueOf(getAmount())).hashCode();
	}
	
	public boolean isPositive() {
		return value > 0;
	}
	
	public boolean isNegative() {
		return value < 0;
	}
	
	public boolean isZero() {
		return value == 0;
	}
	
	/**
	 * Retorna o <code>signum()</code> do BigDecimal
	 * @return
	 * @see BigDecimal#signum()
	 */
	public int signum() {
		return getAmount().signum();
	}
	
	public boolean greaterThan(Money money) {
		return value > money.value;
	}
	
	public boolean lessThan(Money money) {
		return value < money.value;
	}
	
	public boolean greaterThanEquals(Money money) {
		return value >= money.value;
	}
	
	public boolean lessThanEquals(Money money) {
		return value <= money.value;
	}

	public static Money zeroIfNull(Money m){
		return m == null?ZERO:m;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}
	
	public static Money minimumValue(Currency currency) {
		
		return Money.valueOf(Money.ONE.getAmount()
				.movePointLeft(currency.getDefaultFractionDigits()));
	}
	
	public static Money minimumValue(Money proto) {
		
		return minimumValue(proto.getCurrency());
	}
	
	public Money minimumValue() {
		
		return minimumValue(currency);
	}
	
	public Money toPositiveValue(){
		if(isNegative()){
			return multiply(-1);
		}
		return this;
	}
	
	public Money toNegativeValue(){
		if(isPositive()){
			return multiply(-1);
		}
		return this;
	}

	@Override
	public int compareTo(Money o) {
		return ((Long) value).compareTo(o.value);
	}
}


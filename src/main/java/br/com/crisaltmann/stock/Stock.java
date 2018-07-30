package br.com.crisaltmann.stock;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Stock {

	private String code;
	
	private LocalDate data;
	
	private BigDecimal valorFechamento;
	
	private BigDecimal valorAbertura;
	
	private BigDecimal valorMinimo;
	
	private BigDecimal valorMedio;
	
	private BigDecimal valorMaximo;
	
	private Integer volume;
	
	private Integer negocios;

	public Stock(String code, LocalDate data, BigDecimal valorFechamento, BigDecimal valorAbertura, BigDecimal valorMinimo,
			BigDecimal valorMedio, BigDecimal valorMaximo, Integer volume, Integer negocios) {
		super();
		this.code = code;
		this.data = data;
		this.valorFechamento = valorFechamento;
		this.valorAbertura = valorAbertura;
		this.valorMinimo = valorMinimo;
		this.valorMedio = valorMedio;
		this.valorMaximo = valorMaximo;
		this.volume = volume;
		this.negocios = negocios;
	}
	
	public String getCode() {
		return code;
	}

	public LocalDate getData() {
		return data;
	}

	public BigDecimal getValorFechamento() {
		return valorFechamento;
	}

	public BigDecimal getValorAbertura() {
		return valorAbertura;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public BigDecimal getValorMedio() {
		return valorMedio;
	}

	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}

	public Integer getVolume() {
		return volume;
	}

	public Integer getNegocios() {
		return negocios;
	}

	@Override
	public String toString() {
		return "Stock [data=" + data + ", valorFechamento=" + valorFechamento + ", valorAbertura=" + valorAbertura
				+ ", valorMinimo=" + valorMinimo + ", valorMedio=" + valorMedio + ", valorMaximo=" + valorMaximo
				+ ", volume=" + volume + ", negocios=" + negocios + "]";
	}
}

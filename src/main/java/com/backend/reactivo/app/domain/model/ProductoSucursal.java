package com.backend.reactivo.app.domain.model;

public class ProductoSucursal {

	private final Long productoId;
	private final String productoNombre;
	private final Long productoStock;
	private final Long sucursalId;
	private final String sucursalNombre;

	public ProductoSucursal(Long productoId, String productoNombre, Long productoStock, Long sucursalId,
			String sucursalNombre) {
		this.productoId = productoId;
		this.productoNombre = productoNombre;
		this.productoStock = productoStock;
		this.sucursalId = sucursalId;
		this.sucursalNombre = sucursalNombre;
	}

	public Long getProductoId() {
		return productoId;
	}

	public String getProductoNombre() {
		return productoNombre;
	}

	public Long getProductoStock() {
		return productoStock;
	}

	public Long getSucursalId() {
		return sucursalId;
	}

	public String getSucursalNombre() {
		return sucursalNombre;
	}

	@Override
	public String toString() {
		return "ProductoSucursal [productoId=" + productoId + ", productoNombre=" + productoNombre + ", productoStock="
				+ productoStock + ", sucursalId=" + sucursalId + ", sucursalNombre=" + sucursalNombre + "]";
	}

}

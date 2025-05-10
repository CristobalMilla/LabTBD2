package Grupo4.Lab2.DTO;

public class ClienteQueMasAGastadoDTO {
    private long cliente_id;
    private String nombre;
    private int num_pedidos_pagados;
    private int suma_pagos;

    public ClienteQueMasAGastadoDTO(long cliente_id, String nombre, int num_pedidos_pagados, int suma_pagos) {
        this.cliente_id = cliente_id;
        this.nombre = nombre;
        this.num_pedidos_pagados = num_pedidos_pagados;
        this.suma_pagos = suma_pagos;
    }

    public long getCliente_id() {
        return cliente_id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNum_pedidos_pagados() {
        return num_pedidos_pagados;
    }

    public int getSuma_pagos() {
        return suma_pagos;
    }

    public void setCliente_id(long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNum_pedidos_pagados(int num_pedidos_pagados) {
        this.num_pedidos_pagados = num_pedidos_pagados;
    }

    public void setSuma_pagos(int suma_pagos) {
        this.suma_pagos = suma_pagos;
    }
}

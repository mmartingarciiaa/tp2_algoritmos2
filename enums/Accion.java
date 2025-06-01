package enums;

// Enumeración para las acciones posibles en el menú
    public enum Accion {
        AGREGAR_NAVE(1),
        MOVER_NAVE(2),
        AGREGAR_SATELITE(3),
        ATACAR_SECTOR(4),
        ROBAR_CARTA(5),
        USAR_CARTA(6),
        ALIANZA(7),
        DEJAR_DE_JUGAR(8),
        NO_VALIDO(-1);

        private final int codigo;

        Accion(int codigo) {
            this.codigo = codigo;
        }

        public int getCodigo() {
            return codigo;
        }

        public static Accion desdeCodigo(int codigo) {
            for (Accion accion : values()) {
                if (accion.codigo == codigo) {
                    return accion;
                }
            }
            return NO_VALIDO;
        }
    }
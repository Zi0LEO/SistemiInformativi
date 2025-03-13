package main.java.com.opcal.model;

public enum StatoReso {
        RICHIESTO,
        PROCESSATO,
        TERMINATO;

        public static boolean statoPossibile(String daTestare){
                daTestare=daTestare.toUpperCase().trim();
                if (daTestare.equals(RICHIESTO.toString()) || daTestare.equals(PROCESSATO.toString()) || daTestare.equals(TERMINATO.toString())){
                        return true;
                }
                return false;
        }
}

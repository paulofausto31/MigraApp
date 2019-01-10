package migra.br.smart.utils.data;

import java.util.Calendar;

/**
 * Created by r2d2 on 30/03/17.
 */

public class Data {
    private int dia, mes, ano;
    private long dataWithoutSeparator;
    private Calendar calendar = Calendar.getInstance();

    public Data(){

    }

    /***********************************************************************************************
     * @param dia é dia do mês
     * @param mes é o mês que inicia em zero
     * @param ano é o ano
     **********************************************************************************************/
    public Data(int dia, int mes, int ano){
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;

        this.calendar.set(Calendar.HOUR, 0);
        this.calendar.set(Calendar.MINUTE, 0);
        this.calendar.set(Calendar.SECOND, 0);
        this.calendar.set(Calendar.MILLISECOND, 0);
        this.calendar.set(ano, mes, dia);
        //this.calendar.set(ano, mes, dia, 0, 0, 0);
    }

    /***********************************************************************************************
     * @param data do tipo String no formato dd/mm/aaaa
     **********************************************************************************************/
    public Data(String data){
        String[] partsDat = data.split("/");
        this.dia = Integer.parseInt(partsDat[0]);
        this.mes = Integer.parseInt(partsDat[1])-1;
        this.ano = Integer.parseInt(partsDat[2]);

        this.calendar.set(Calendar.HOUR, 0);
        this.calendar.set(Calendar.MINUTE, 0);
        this.calendar.set(Calendar.SECOND, 0);
        this.calendar.set(Calendar.MILLISECOND, 0);

        this.calendar.set(ano, mes, dia);
    }

    /***********************************************************************************************
     * @param data do tipo long em milisegundos
     **********************************************************************************************/
    public Data(long data){
        this.calendar.setTimeInMillis(data);
        this.dia = this.calendar.get(Calendar.DAY_OF_MONTH);
        this.mes = this.calendar.get(Calendar.MONTH);
        this.ano = this.calendar.get(Calendar.YEAR);
    }

    public String getDataWithoutSeparator() {
        String data = "";
        if(this.calendar != null) {
            data = ""+this.calendar.get(Calendar.DAY_OF_MONTH) +
                    this.calendar.get(Calendar.MONTH) + this.calendar.get(Calendar.YEAR);
        }
        return data;
    }

    /**
     * @param dataWithoutSeparator data no formato dd/mm/aaaa
     * @return data no formato ddmmaaaa como double
     */
    public long getDataWithoutSeparator(String dataWithoutSeparator) {
        String[] data = dataWithoutSeparator.split("/");
        if(data.length == 3){
            this.dataWithoutSeparator = Long.parseLong(data[0]+Long.parseLong(data[1])+data[2]);
        }else{
            this.dataWithoutSeparator = 0;
        }
        return this.dataWithoutSeparator;
    }

    public void setDataWithoutSeparator(long dataWithoutSeparator) {
        this.dataWithoutSeparator = dataWithoutSeparator;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    /***********************************************************************************************
     * @return data em milisegundos
     **********************************************************************************************/
    public long getDataInMillis(){
        return this.calendar.getTimeInMillis();
    }

    /**
     * @return long data em milisegundos sem hora, minuto ou segundo
     */
    public long getOnlyDataInMillis(){
        this.calendar.set(Calendar.HOUR, 0);
        this.calendar.set(Calendar.MINUTE, 0);
        this.calendar.set(Calendar.SECOND, 0);
        this.calendar.set(Calendar.MILLISECOND, 0);
        return this.calendar.getTimeInMillis();
    }

    /***********************************************************************************************
     * @return data no formato dd/mm/aaaa
     **********************************************************************************************/
    public String getStringData(){
        return this.calendar.get(Calendar.DAY_OF_MONTH)+"/"+(this.calendar.get(Calendar.MONTH)+1)+"/"+
                this.calendar.get(Calendar.YEAR);
    }
}

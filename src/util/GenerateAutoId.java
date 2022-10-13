package util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerateAutoId {
    public  static String autoId(String sql,int columnNumber,int stringCount,String sampleId){

        try{
            ResultSet result = CrudUtil.execute(sql);

            if(result.next()){

                String idString=result.getString(columnNumber); //getId as a String
                int idLength=idString.length();
                String txt= idString.substring(0,stringCount);// First Strings
                String num=idString.substring(stringCount,idLength);//Last Numbers

                int n=Integer.parseInt(num); //get last Numbers as Int and ++
                n++;
                if(n==2){
                    txt=txt.concat("0");
                }else if(n==3){
                    txt=txt.concat("0");
                }else if(n==4){
                    txt=txt.concat("0");
                }else if(n==5){
                    txt=txt.concat("0");
                }else if(n==6){
                    txt=txt.concat("0");
                }else if(n==7){
                    txt=txt.concat("0");
                }else if(n==8){
                    txt=txt.concat("0");
                }else if(n==9){
                    txt=txt.concat("0");
                }

                String sNum=Integer.toString(n);

                return txt+sNum; //return
            }

        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return sampleId;
    }
}



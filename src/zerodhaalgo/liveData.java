/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zerodhaalgo;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Quote;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;
import static zerodhaalgo.mainFrame.jTable1;
import static zerodhaalgo.mainFrame.kiteSdk;

/**
 *
 * @author Amol
 */
public class liveData implements Runnable{

    DefaultTableModel model;
    
    liveData()
    {
        model=(DefaultTableModel)jTable1.getModel();
    }
    @Override
    public void run() {
        
        String instrument[]=new String[model.getRowCount()];
        for(int i=0;i<model.getRowCount();i++)
        {
            instrument[i]="NSE:"+model.getValueAt(i, 0).toString();
            
        }
        while(true){
        try {
            Map<String,Quote> quotes=kiteSdk.getQuote(instrument);
           
            for(int i=0;i<model.getRowCount();i++)
            {
                double volume=quotes.get(instrument[i]).volumeTradedToday;
                System.out.println(quotes.get(instrument[i]).volumeTradedToday);
                double ltp=quotes.get(instrument[i]).lastPrice;
                double high=quotes.get(instrument[i]).ohlc.high;
                double low=quotes.get(instrument[i]).ohlc.low;
                double close=quotes.get(instrument[i]).ohlc.close;
                double change=(ltp-close)/close*100;
                model.setValueAt(high, i, 1);
                model.setValueAt(low, i, 2);
                model.setValueAt(volume, i, 3);
                model.setValueAt(ltp, i, 4);
                model.setValueAt(change, i, 5);
            }
            
        } catch (KiteException | JSONException | IOException ex) {
            Logger.getLogger(liveData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    }
    
}

public class URLModel {


protected String getUrl() {

        String key = "1A79MCHMT69G16RE";
        String asset = "AAPL";
        String intervall = "1min";
        String function = "TIME_SERIES_INTRADAY";
        String url = "https://www.alphavantage.co/query?function="+function+"&symbol="+asset+"&interval="+ intervall+"&apikey="+key;
    return url;


    }

}

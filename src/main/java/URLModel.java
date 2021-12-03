public class URLModel {

    protected String key;
    protected String asset;
    protected String interval;
    protected String function;
    protected String url;


    /**

    Class with getter and setter for the ALPHAVANTAGE URL

     */

    public URLModel (){

    }

    public URLModel (String key, String asset, String interval, String function){
        this.key = key;
        this.asset = asset;
        this.interval = interval;
        this.function = function;
    }



    public String getKey(){
        return key;
    }

    public String getAsset(){
        return asset;
    }

    public String getInterval(){
        return interval;
    }

    public String getFunction(){
        return function;
    }

    public String getUrl(){
        setUrl();
        return url;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setUrl() {
        String url ="https://www.alphavantage.co/query?function="+function+"&symbol="+asset+"&interval="+ interval+"&apikey="+key;
        this.url = url;
    }

}

package util.jayflot.spider;
import java.util.ArrayList;

import com.google.gson.Gson;

import util.jayflot.spider.data.SpiderPlotData;
import br.blog.arruda.plot.Plot;
import br.blog.arruda.plot.data.PlotData;

public class SpiderMainPlot extends Plot {
	
	private ArrayList<SpiderPlotData> datas;
	
	public ArrayList<SpiderPlotData> getSpiderDatas() {
		return new ArrayList<SpiderPlotData>();
	}

	public void setSpiderDatas(ArrayList<SpiderPlotData> datas) {
		this.datas = datas;
	}

	/**
	 * Return a string that represents the data in JSON format.
	 * @return
	 */
	public String printData(){
		Gson gson = new Gson();
		String json="";
		
		//print all datas:
		json+="[";
		for(PlotData data: this.datas){
			String temp = gson.toJson(data,data.getClass());
			json+=temp+",";
		}
		json+="]";
		
		return json;
	}

}

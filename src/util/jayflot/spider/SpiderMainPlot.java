package util.jayflot.spider;
import java.util.ArrayList;


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
}

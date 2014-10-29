package util.jayflot.spider.data;
import util.jayflot.spider.tipo.PlotSpider;
import br.blog.arruda.plot.data.PlotData;

public class SpiderPlotData extends PlotData{

	private PlotSpider spider;

	public SpiderPlotData() {
		this.spider = null;
	}

	public PlotSpider getSpider() {
		return spider;
	}

	public void setSpider(PlotSpider spider) {
		this.spider = spider;
	}
}

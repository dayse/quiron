package util.jayflot.spider.opt;
import util.jayflot.spider.tipo.PlotSpider;
import br.blog.arruda.plot.opt.PlotSeries;

/**
 * PlotSeries com opções relativas ao pluging Spider (grafico de Radar/Spider)
 * @author arruda
 *
 */
public class SpiderPlotSeries extends PlotSeries{
	
	private PlotSpider spider;

	public SpiderPlotSeries(){
		this.spider = null;
	}

	public PlotSpider getSpider() {
		return spider;
	}

	public void setSpider(PlotSpider spider) {
		this.spider = spider;
	}
	
	
}

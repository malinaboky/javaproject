import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Map;


public class CountryChart extends JFrame{
    public CountryChart(Map<String, Float> data, double middle){
        initUI(data, middle);
    }

    private void initUI(Map<String, Float> data, double middle) {

        SlidingCategoryDataset dataset = createDataset(data);

        JFreeChart chart = createChart(dataset, middle);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(850, 750));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(chartPanel);

        JScrollBar scroller = new JScrollBar(SwingConstants.HORIZONTAL, 0, 10, 0, data.size());
        scroller.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                dataset.setFirstCategoryIndex(scroller.getValue());
            }
        });

        JPanel scrollPanel = new JPanel(new BorderLayout());
        scrollPanel.add(scroller);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        add(scrollPanel, BorderLayout.SOUTH);

        pack();
        setTitle("Values of health");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private SlidingCategoryDataset createDataset(Map<String, Float> data){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String key : data.keySet())
            dataset.setValue(data.get(key), "Health", key);
        return new SlidingCategoryDataset(dataset,0, 7);
    }

    private JFreeChart createChart(SlidingCategoryDataset dataset, double middle)
    {
        CategoryAxis categoryAxis = new CategoryAxis("");
        ValueAxis valueAxis = new NumberAxis("Health");
        BarRenderer renderer = new BarRenderer() {
            @Override
            public Paint getItemPaint(int row, int column) {
                if (Double.parseDouble(dataset.getValue(row, column).toString()) >= middle * 1.25)
                    return Color.green;
                else if (Double.parseDouble(dataset.getValue(row, column).toString()) <= middle * 0.75)
                    return Color.red;
                return Color.yellow;
            }
        };
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER));
        renderer.setBaseItemLabelsVisible(Boolean.TRUE);
        renderer.setBarPainter(new StandardBarPainter());

        CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis, renderer);

        JFreeChart barChart = new JFreeChart("Health country", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        barChart.setAntiAlias(true);
        return barChart;
    }
}

//Handles functionality of Distributions
$(window).load(function () {
  clt();
});

//*******************************************************************************//
//Central Limit Theorem
//*******************************************************************************//

function clt() {
  // define width, height, margin
  var margin = { top: 15, right: 5, bottom: 15, left: 5 };
  var width = 800; //parseInt(d3.select("#graph").style("width")) - margin.left - margin.right,
  height = 500;
  // create svg
  var svg_clt = d3
    .select("#graph")
    .append("svg")
    .attr("width", "100%")
    .attr("height", "100%")
    .attr(
      "viewBox",
      "0 0 " +
        (width + margin.left + margin.right) +
        " " +
        (height + margin.top + margin.bottom)
    )
    .attr("preserveAspectRatio", "xMidYMid meet")
    .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

  // constants
  var dt = 100,
    n = 1,
    draws = 1,
    alpha = 1,
    beta = 1,
    y1 = height / 3,
    y2 = height / 4,
    bins = 10, // # of items
    counts = [],
    interval_clt;

  var data_json = [
    { name: "a", prob: 0.15 },
    { name: "b", prob: 0.05 },
    { name: "c", prob: 0.15 },
    { name: "d", prob: 0.05 },
    { name: "e", prob: 0.15 },
    { name: "f", prob: 0.05 },
    { name: "g", prob: 0.15 },
    { name: "h", prob: 0.05 },
    { name: "i", prob: 0.15 },
    { name: "j", prob: 0.05 },
  ];

  var data_prep = [
    0, 0, 0, 0.1, 0.2, 0.2, 0.2, 0.3, 0.4, 0, 4, 0.4, 0.5, 0.6, 0.6, 0.6, 0.7,
    0.8, 0.8, 0.8, 0.9,
  ];

  // scales
  var x_scale_clt = d3.scale.linear().domain([0, 1]).range([0, width]);
  var y_scale_clt = d3.scale
    .linear()
    .domain([0, 3])
    .range([0, height - 2 * y1]);
  var z_scale_clt = d3.scale.linear().domain([0, 3]).range([0, y1]);

  // clip path
  var clip_clt = svg_clt
    .append("clipPath")
    .attr("id", "view_clt")
    .append("rect")
    .attr("x", 0)
    .attr("y", height - (2 * y1 - y2))
    .attr("width", width)
    .attr("height", 2 * y1 - y2);

  // draw horizontal bar
  function draw_bar(selection, dy, label) {
    // group
    var axis = selection.append("g").attr("class", "axis");
    // bar
    axis
      .append("line")
      .attr("x1", x_scale_clt(0))
      .attr("x2", x_scale_clt(1))
      .attr("y1", dy)
      .attr("y2", dy);
    // label
    axis
      .append("text")
      .attr("x", x_scale_clt(0))
      .attr("y", dy)
      .attr("dy", "1em")
      .text(label);
  }
  // create three bars
  svg_clt.call(draw_bar, y1, "draw");
  svg_clt.call(draw_bar, 3 * y1, "count");

  // path and area elements
  var sampling_path = svg_clt.append("path").attr("id", "pdf"),
    sampling_area = svg_clt.append("path").attr("id", "pdfArea"),
    theoretical_path = svg_clt
      .append("path")
      .attr("id", "cdf")
      .attr("opacity", 0)
      .attr("clip-path", "url(#view_clt)")
      .moveToBack();

  var probability = d3.layout
    .histogram()
    .bins(x_scale_clt.ticks(bins))
    .frequency(false);
  var p = svg_clt.append("g").attr("class", "histogram");

  function draw_probability() {
    var data = probability(data_prep);
    console.log(data);
    var ymax = d3.max(
      data.map(function (d) {
        return d.y;
      })
    );
    y_scale_clt.domain([0, ymax * bins]);
    // enter bars
    var bar = p.selectAll("g").data(data);
    var barEnter = bar.enter().append("g").attr("class", "bar");
    barEnter.append("rect");
    barEnter
      .append("text")
      .attr("y", y1 - 15)
      .attr("text-anchor", "middle");
    // update bars
    bar
      .select("rect")
      .attr("x", function (d) {
        return x_scale_clt(d.x) + 1;
      })
      .attr("width", x_scale_clt(data[0].dx) - 1)
      .transition()
      .duration(250)
      .attr("y", function (d) {
        return y1 - y_scale_clt(d.y * bins);
      })
      .attr("height", function (d) {
        return y_scale_clt(d.y * bins);
      });
    bar
      .select("text")
      .attr("x", function (d) {
        return x_scale_clt(d.x + 1 / (2 * bins));
      })
      .text(function (d) {
        return d.y > 0 ? d3.format("%")(d.y) : "";
      });
    // exit bars
    bar.exit().remove();
  }

  // create histogram
  var histogram = d3.layout
    .histogram()
    .bins(x_scale_clt.ticks(bins))
    .frequency(false);
  var bars = svg_clt.append("g").attr("class", "histogram");

  function draw_histogram() {
    // get histrogram of counts
    var data = histogram(counts);
    // update scale
    var ymax = d3.max(
      data.map(function (d) {
        return d.y;
      })
    );
    y_scale_clt.domain([0, ymax * bins]);
    // enter bars
    var bar = bars.selectAll("g").data(data);
    var barEnter = bar.enter().append("g").attr("class", "bar");
    barEnter.append("rect");
    barEnter
      .append("text")
      .attr("y", 3 * y1 - 15)
      .attr("text-anchor", "middle");
    // update bars
    bar
      .select("rect")
      .attr("x", function (d) {
        return x_scale_clt(d.x) + 1;
      })
      .attr("width", x_scale_clt(data[0].dx) - 1)
      .transition()
      .duration(250)
      .attr("y", function (d) {
        return 3 * y1 - y_scale_clt(d.y * bins);
      })
      .attr("height", function (d) {
        return y_scale_clt(d.y * bins);
      });
    bar
      .select("text")
      .attr("x", function (d) {
        return x_scale_clt(d.x + 1 / (2 * bins));
      })
      .text(function (d) {
        return d.y > 0 ? d3.format("%")(d.y) : "";
      });
    // exit bars
    bar.exit().remove();
  }

  // Creates Circles and transitions
  function tick() {
    // take samples
    var data = [];
    var rand = Math.random();
    var idx;

    for (idx = 0; idx < bins; idx++) {
      rand -= data_json[idx].prob;
      if (rand <= 0) break;
    }
    data.push(idx / bins + 1 / (bins * 2));
    // add balls
    var group = svg_clt.append("g").attr("class", "ball-group");
    var balls = group.selectAll(".ball").data(data);
    // animate balls
    var i = 0,
      j = 0;
    balls
      .enter()
      .append("circle")
      .attr("class", "ball")
      .attr("cx", function (d) {
        return x_scale_clt(d);
      })
      .attr("cy", y1)
      .attr("r", 5)
      .transition()
      .duration(dt)
      .each(function () {
        ++i;
      })
      .each("end", function () {
        if (!--i) {
          balls
            .transition()
            .duration(400)
            .attr("cx", x_scale_clt(idx / bins + 1 / (bins * 2)))
            .style("fill", "#FF8B22")
            .transition()
            .duration(400)
            .attr("cy", 3 * y1 - 3)
            .attr("r", 3)
            .each(function () {
              ++j;
            })
            .each("end", function () {
              if (!--j) {
                counts.push(idx / bins);
                draw_histogram();
                draw_probability();
              }
              d3.select(this).remove();
            });
        }
      });
  }

  // initiate sampling
  function start_sampling() {
    dt = 350 / Math.pow(1.04, draws);
    var count = 0;
    interval_clt = setInterval(function () {
      tick();
      if (++count === draws) {
        clearInterval(interval_clt);
      }
    }, dt);
  }

  // reset and clear CLT
  function reset_clt() {
    clearInterval(interval_clt);
    counts = [];
    d3.timer.flush();
    svg_clt.selectAll("circle").remove();
    svg_clt.selectAll(".bar").remove();
    y_scale_clt.domain([0, 3]);
    draw_probability();
  }

  // update alpha
  $("#alpha_clt").on("input", function (e) {
    alpha = parseFloat($(this).val());
    d3.select("#alpha_clt-value").text(round(alpha, 2));
    reset_clt();
  });

  // update beta
  $("#beta_clt").on("input", function (e) {
    beta = parseFloat($(this).val());
    d3.select("#beta_clt-value").text(round(beta, 2));
    reset_clt();
  });

  // update sample size
  d3.select("#sample").on("input", function () {
    n = +this.value;
    d3.select("#sample-value").text(n);
    reset_clt();
  });

  // update number of draws
  d3.select("#draws").on("input", function () {
    draws = +this.value;
    d3.select("#draws-value").text(draws);
  });

  // drop balls
  $("#form_clt").click(function () {
    clearInterval(interval_clt);
    start_sampling();
  });

  draw_probability();
}

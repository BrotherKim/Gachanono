//Handles functionality of Distributions
$(window).load(function () {
  clt();
});

function clt() {
  // define width, height, margin
  var margin = { top: 50, right: 5, bottom: 15, left: 5 };
  var width = 800;
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
    draws = 1,
    y1 = height / 3,
    y2 = height / 4,
    bins = 10, // # of items
    counts = [],
    interval_clt,
    total_cnt = 0, // 누적횟수
    flag = true;

  var avail_money = document.getElementById("avail_money");
  var item_cost = document.getElementById("item_cost");
  var left_money = document.getElementById("left_money");
  var total_try = document.getElementById("total_try");
  avail_money.onchange = function () {
    left_money.innerText = avail_money.value;
  };

  // json 파일 받아오기 (ajax)
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

  data_prep = [];
  function test() {
    for (var i = 0; i < data_json.length; i++) {
      for (var j = 0; j < data_json[i].prob * 10000; j++) {
        data_prep.push(i / bins);
      }
    }
  }
  test();

  // scales
  var x_scale_clt = d3.scale.linear().domain([0, 1]).range([0, width]);
  var y_scale_clt = d3.scale
    .linear()
    .domain([0, 3])
    .range([0, height - 2 * y1]);

  var ticks_array = [];
  function custom_ticks() {
    for (var i = 0; i < bins; i++) {
      ticks_array.push(i / bins);
    }
    ticks_array.push(1);
  }
  custom_ticks();

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

  var tipProb = d3
    .tip()
    .attr("id", "tipProb")
    .attr("class", "d3-tip")
    .offset([-10, 0]);

  var tipHisto = d3
    .tip()
    .attr("id", "tipHisto")
    .attr("class", "d3-tip")
    .offset([-10, 0]);

  var tipProb_name = d3
    .tip()
    .attr("id", "tipHisto_name")
    .attr("class", "d3-tip");

  var tipHisto_name = d3
    .tip()
    .attr("id", "tipHisto_name")
    .attr("class", "d3-tip");

  tipProb.html(function (d) {
    return d.y > 0 ? d3.format(".2%")(d.y) : "";
  });

  tipHisto.html(function (d) {
    return d.y > 0 ? d3.format(".2%")(d.y) : "";
  });

  tipProb_name.html(function (d, i) {
    return data_json[i].name;
  });

  tipHisto_name.html(function (d, i) {
    return data_json[i].name;
  });

  // create two bars
  svg_clt.call(draw_bar, y1, "draw");
  svg_clt.call(draw_bar, 3 * y1, "count");
  svg_clt.call(tipProb);
  svg_clt.call(tipHisto);
  svg_clt.call(tipProb_name);
  svg_clt.call(tipHisto_name);

  var probability = d3.layout.histogram().bins(ticks_array).frequency(false);
  var p = svg_clt.append("g").attr("class", "histogram");

  function draw_probability() {
    var data = probability(data_prep);
    //console.log(data);
    var ymax = d3.max(
      data.map(function (d) {
        return d.y;
      })
    );
    y_scale_clt.domain([0, ymax * bins]);

    // enter bars
    var bar = p.selectAll("g").data(data);

    var barEnter = bar.enter().append("g").attr("class", "prob");
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

    bar.on("mouseover", tipProb_name.show).on("mouseout", tipProb_name.hide);

    if (bins <= 25) {
      bar
        .select("text")
        .attr("x", function (d) {
          return x_scale_clt(d.x + 1 / (2 * bins));
        })
        .text(function (d) {
          return d.y > 0 ? d3.format("%")(d.y) : "";
        });
    } else {
      bar.each(function () {
        d3.select(this)
          .on("mouseover", tipProb.show)
          .on("mouseout", tipProb.hide);
      });
    }

    // exit bars
    bar.exit().remove();
  }

  function check_pop() {
    for (var i = 0; i < bins; i++) {
      if (JSON.stringify(data_histo[i]) == JSON.stringify([])) {
        return false;
      }
    }
    return true;
  }

  // create histogram
  var histogram = d3.layout.histogram().bins(ticks_array).frequency(false);

  var bars = svg_clt.append("g").attr("class", "histogram");

  var data_histo = [[]];
  function draw_histogram() {
    // get histrogram of counts
    data_histo = histogram(counts);
    console.log(data_histo);

    total_try.innerText = total_cnt;
    left_money.innerText = left_money.innerText - item_cost.value;

    if (flag && check_pop()) {
      flag = false;

      var total_cost = total_cnt * item_cost.value;

      document.getElementById("success_test").innerText =
        " 모든 아이템이 뽑힐 때 까지 시도횟수 " +
        String(total_cnt) +
        "회, 총 금액 " +
        String(total_cost) +
        "원 소비되었습니다.";
    }

    // update scale
    var ymax = d3.max(
      data_histo.map(function (d) {
        return d.y;
      })
    );
    y_scale_clt.domain([0, ymax * bins]);

    // enter bars
    var bar = bars.selectAll("g").data(data_histo);

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
      .attr("width", x_scale_clt(data_histo[0].dx) - 1)
      .transition()
      .duration(250)
      .attr("y", function (d) {
        return 3 * y1 - y_scale_clt(d.y * bins);
      })
      .attr("height", function (d) {
        return y_scale_clt(d.y * bins);
      });

    bar.on("mouseover", tipHisto_name.show).on("mouseout", tipHisto_name.hide);

    if (bins <= 25) {
      bar
        .select("text")
        .attr("x", function (d) {
          return x_scale_clt(d.x + 1 / (2 * bins));
        })
        .text(function (d) {
          return d.y > 0 ? d3.format("%")(d.y) : "";
        });
    } else {
      bar.each(function () {
        d3.select(this)
          .on("mouseover", tipHisto.show)
          .on("mouseout", tipHisto.hide);
      });
    }

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
    var count = 0;

    interval_clt = setInterval(function () {
      count++;
      total_cnt++;

      tick();

      if (count === draws) {
        clearInterval(interval_clt);
      }
    }, dt);
  }

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

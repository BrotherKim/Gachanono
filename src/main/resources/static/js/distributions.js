//Handles functionality of Distributions
/*var data_prep = [];
var bins = 0;
*/
$(window).load(function () {
  clt();
  /*$.ajax({
    url: "",
    dataType: "json",
    success: function (data) {
      $.each(data, function () {
        for (var i = 0; i < this.prob * 10000; i++) {
          data_prep.append();
        }
      });
    },
  });*/
});

//*******************************************************************************//
//Central Limit Theorem
//*******************************************************************************//

function clt() {
  // define width, height, margin
  var margin = { top: 50, right: 5, bottom: 15, left: 5 };
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
    draws = 1,
    y1 = height / 3,
    y2 = height / 4,
    bins = 100, // # of items
    counts = [],
    interval_clt,
    total_cnt = 0, // 누적횟수
    cost = 1;

  // json 파일 받아오기 (ajax)
  var data_json = [
    /*{ name: "a", prob: 0.04 },
    { name: "b", prob: 0.04 },
    { name: "c", prob: 0.04 },
    { name: "d", prob: 0.04 },
    { name: "e", prob: 0.04 },
    { name: "f", prob: 0.04 },
    { name: "g", prob: 0.04 },
    { name: "h", prob: 0.04 },
    { name: "i", prob: 0.04 },
    { name: "j", prob: 0.04 },
    { name: "k", prob: 0.04 },
    { name: "l", prob: 0.04 },
    { name: "m", prob: 0.04 },
    { name: "n", prob: 0.04 },
    { name: "o", prob: 0.04 },
    { name: "p", prob: 0.04 },
    { name: "q", prob: 0.04 },
    { name: "r", prob: 0.04 },
    { name: "s", prob: 0.04 },
    { name: "t", prob: 0.04 },
    { name: "u", prob: 0.04 },
    { name: "v", prob: 0.04 },
    { name: "w", prob: 0.04 },
    { name: "x", prob: 0.04 },
    { name: "y", prob: 0.04 },*/ // bins=25

    /*{ name: "a", prob: 0.15 },
    { name: "b", prob: 0.05 },
    { name: "c", prob: 0.15 },
    { name: "d", prob: 0.05 },
    { name: "e", prob: 0.15 },
    { name: "f", prob: 0.05 },
    { name: "g", prob: 0.15 },
    { name: "h", prob: 0.05 },
    { name: "i", prob: 0.15 },
    { name: "j", prob: 0.05 },*/ // bins=10
    { name: "a", prob: 0.015 },
    { name: "b", prob: 0.005 },
    { name: "c", prob: 0.015 },
    { name: "d", prob: 0.005 },
    { name: "e", prob: 0.015 },
    { name: "f", prob: 0.005 },
    { name: "g", prob: 0.015 },
    { name: "h", prob: 0.005 },
    { name: "i", prob: 0.015 },
    { name: "j", prob: 0.005 },
    { name: "a", prob: 0.015 },
    { name: "b", prob: 0.005 },
    { name: "c", prob: 0.015 },
    { name: "d", prob: 0.005 },
    { name: "e", prob: 0.015 },
    { name: "f", prob: 0.005 },
    { name: "g", prob: 0.015 },
    { name: "h", prob: 0.005 },
    { name: "i", prob: 0.015 },
    { name: "j", prob: 0.005 },
    { name: "a", prob: 0.015 },
    { name: "b", prob: 0.005 },
    { name: "c", prob: 0.015 },
    { name: "d", prob: 0.005 },
    { name: "e", prob: 0.015 },
    { name: "f", prob: 0.005 },
    { name: "g", prob: 0.015 },
    { name: "h", prob: 0.005 },
    { name: "i", prob: 0.015 },
    { name: "j", prob: 0.005 },
    { name: "a", prob: 0.015 },
    { name: "b", prob: 0.005 },
    { name: "c", prob: 0.015 },
    { name: "d", prob: 0.005 },
    { name: "e", prob: 0.015 },
    { name: "f", prob: 0.005 },
    { name: "g", prob: 0.015 },
    { name: "h", prob: 0.005 },
    { name: "i", prob: 0.015 },
    { name: "j", prob: 0.005 },
    { name: "a", prob: 0.015 },
    { name: "b", prob: 0.005 },
    { name: "c", prob: 0.015 },
    { name: "d", prob: 0.005 },
    { name: "e", prob: 0.015 },
    { name: "f", prob: 0.005 },
    { name: "g", prob: 0.015 },
    { name: "h", prob: 0.005 },
    { name: "i", prob: 0.015 },
    { name: "j", prob: 0.005 },
    { name: "a", prob: 0.015 },
    { name: "b", prob: 0.005 },
    { name: "c", prob: 0.015 },
    { name: "d", prob: 0.005 },
    { name: "e", prob: 0.015 },
    { name: "f", prob: 0.005 },
    { name: "g", prob: 0.015 },
    { name: "h", prob: 0.005 },
    { name: "i", prob: 0.015 },
    { name: "j", prob: 0.005 },
    { name: "a", prob: 0.015 },
    { name: "b", prob: 0.005 },
    { name: "c", prob: 0.015 },
    { name: "d", prob: 0.005 },
    { name: "e", prob: 0.015 },
    { name: "f", prob: 0.005 },
    { name: "g", prob: 0.015 },
    { name: "h", prob: 0.005 },
    { name: "i", prob: 0.015 },
    { name: "j", prob: 0.005 },
    { name: "a", prob: 0.015 },
    { name: "b", prob: 0.005 },
    { name: "c", prob: 0.015 },
    { name: "d", prob: 0.005 },
    { name: "e", prob: 0.015 },
    { name: "f", prob: 0.005 },
    { name: "g", prob: 0.015 },
    { name: "h", prob: 0.005 },
    { name: "i", prob: 0.015 },
    { name: "j", prob: 0.005 },
    { name: "a", prob: 0.015 },
    { name: "b", prob: 0.005 },
    { name: "c", prob: 0.015 },
    { name: "d", prob: 0.005 },
    { name: "e", prob: 0.015 },
    { name: "f", prob: 0.005 },
    { name: "g", prob: 0.015 },
    { name: "h", prob: 0.005 },
    { name: "i", prob: 0.015 },
    { name: "j", prob: 0.005 },
    { name: "a", prob: 0.015 },
    { name: "b", prob: 0.005 },
    { name: "c", prob: 0.015 },
    { name: "d", prob: 0.005 },
    { name: "e", prob: 0.015 },
    { name: "f", prob: 0.005 },
    { name: "g", prob: 0.015 },
    { name: "h", prob: 0.005 },
    { name: "i", prob: 0.015 },
    { name: "j", prob: 0.005 }, //bins=100
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
  var z_scale_clt = d3.scale.linear().domain([0, 3]).range([0, y1]);

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

  tipProb.html(function (d) {
    return d.y > 0 ? d3.format(".2%")(d.y) : "";
  });

  tipHisto.html(function (d) {
    return d.y > 0 ? d3.format(".2%")(d.y) : "";
  });

  // create two bars
  svg_clt.call(draw_bar, y1, "draw");
  svg_clt.call(draw_bar, 3 * y1, "count");
  svg_clt.call(tipProb);
  svg_clt.call(tipHisto);

  var probability = d3.layout.histogram().bins(ticks_array).frequency(false);
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

  // create histogram
  var histogram = d3.layout.histogram().bins(ticks_array).frequency(false);

  var bars = svg_clt.append("g").attr("class", "histogram");

  var data_histo = [[]];
  function draw_histogram() {
    // get histrogram of counts
    data_histo = histogram(counts);

    console.log(data_histo);
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
      bar.each(function (d) {
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

  function check_pop() {
    for (var i = 0; i < bins; i++) {
      if (JSON.stringify(data_histo[i]) == JSON.stringify([])) {
        return false;
      }
    }
    console.log("pop");
    return true;
  }

  var flag = true;

  // initiate sampling
  function start_sampling() {
    //dt = 350 / Math.pow(1.04, draws);
    var count = 0;

    interval_clt = setInterval(function () {
      tick();
      count++;
      total_cnt++;

      flag = true;

      if (flag && check_pop()) {
        flag = false;

        var total_cost = total_cnt * cost;

        document.getElementById("success_test").innerText =
          " 모든 아이템이 뽑힐 때 까지 시도횟수 " +
          String(total_cnt) +
          "회, 총 금액 " +
          String(total_cost) +
          "원 소비되었습니다.";

        /*
        // 여기에 창띄우기
        toastr.options = {
          closeButton: true,
          debug: false,
          newestOnTop: false,
          progressBar: false,
          positionClass: "toast-bottom-right",
          preventDuplicates: false,
          onclick: null,
          showDuration: "2000",
          hideDuration: "1000",
          timeOut: "5000",
          extendedTimeOut: "1000",
          showEasing: "swing",
          hideEasing: "linear",
          showMethod: "fadeIn",
          hideMethod: "fadeOut",
        };
        toastr["success"](
          " 모든 아이템이 뽑힐 때 까지<br> 시도횟수 " +
            String(total_cnt) +
            "회,<br> 총 금액 " +
            String(total_cost) +
            "원<br> 소비되었습니다."
        );*/
      }

      if (count === draws) {
        flag = false;
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

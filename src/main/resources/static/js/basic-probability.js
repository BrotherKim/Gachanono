//Handles functionality of Probability
$(window).load(function () {
  chance();
});

//Handles CSS animation for coin and die
//Adapted from http://jsfiddle.net/byrichardpowell/38MGS/1/
$.fn.animatecss = function (anim, time, cb) {
  if (time) this.css("-webkit-transition", time / 1000 + "s");
  this.addClass(anim);
  if ($.isFunction(cb)) {
    setTimeout(
      function () {
        $(this).each(cb);
      },
      time ? time : 250
    );
  }
  return this;
};

//Cumulative Sum function for array
function cumsum(array) {
  var resultArray = [];
  array.reduce(function (a, b, i) {
    return (resultArray[i] = a + b.p);
  }, 0);
  return resultArray;
}

//*******************************************************************************//
//Chance Events
//*******************************************************************************//
function chance() {
  //Constants
  var probTheo = [0.5, 0.5];
  var countCoin = [0, 0];
  var coinData = [
    {
      data: [
        { value: countCoin[0], side: "head" },
        { value: countCoin[1], side: "tail" },
      ],
      state: "Observed outcomes",
    },
    {
      data: [
        { value: probTheo[0], side: "head" },
        { value: probTheo[1], side: "tail" },
      ],
      state: "True probabilities",
    },
  ];

  var margin = { top: 15, right: 5, bottom: 15, left: 5 };
  var width = 800; //parseInt(d3.select("#graph").style("width")) - margin.left - margin.right,
  height = 500;

  //Create SVG
  var svgCoin = d3
    .select("#barCoin")
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
    .attr("preserveAspectRatio", "xMidYMid meet");

  //Create Container
  var containerCoin = svgCoin.append("g");

  //Create Scales
  var yScaleCoin = d3.scale.linear().domain([0, 1]);
  var x0ScaleCoin = d3.scale
    .ordinal()
    .domain(["Observed outcomes", "True probabilities"]);
  var x1ScaleCoin = d3.scale.ordinal().domain(["head", "tail"]);

  //Drag function for coin bar chart
  var dragCoin = d3.behavior
    .drag()
    .origin(function () {
      var rect = d3.select(this);
      return { x: rect.attr("x"), y: rect.attr("y") };
    })
    .on("drag", function (d) {
      var y = Math.min(1, Math.max(0, yScaleCoin.invert(d3.event.y)));
      if (d3.select(this).attr("class") == "head") probTheo = [y, 1 - y];
      else probTheo = [1 - y, y];
      d.value = y;
      tipCoinTheo.show(d, this);
      countCoin = [0, 0];
      updateCoin(0);
    });

  //Create SVG Elements
  var states = containerCoin
    .selectAll("g.state")
    .data(coinData)
    .enter()
    .append("g")
    .attr("class", "state");

  var rects = states
    .selectAll("rect")
    .data(function (d) {
      return d.data;
    })
    .enter()
    .append("rect");

  var axisCoin = svgCoin.append("g").attr("class", "x axis");

  var xAxisCoin = d3.svg.axis().scale(x0ScaleCoin).orient("bottom").ticks(0);

  //Create tool tips for observed and expected
  var tipCoinObs = d3
    .tip()
    .attr("id", "tipCoinObs")
    .attr("class", "d3-tip")
    .offset([-10, 0]);
  var tipCoinTheo = d3
    .tip()
    .attr("id", "tipCoinTheo")
    .attr("class", "d3-tip")
    .offset([-10, 0]);

  //Update rectangles and text
  function updateCoin(t) {
    var total = Math.max(1, countCoin[0] + countCoin[1]);
    var probObs = [countCoin[0] / total, countCoin[1] / total];
    coinData[0].data[0].value = probObs[0];
    coinData[0].data[1].value = probObs[1];
    coinData[1].data[0].value = probTheo[0];
    coinData[1].data[1].value = probTheo[1];

    tipCoinObs.html(function (d) {
      return (
        round(d.value, 2) + " = " + round(d.value * total, 0) + "/" + total
      );
    });
    tipCoinTheo.html(function (d, i) {
      return round(d.value, 2);
    });

    states
      .attr("transform", function (d) {
        return "translate(" + x0ScaleCoin(d.state) + "," + 0 + ")";
      })
      .attr("class", function (d) {
        return d.state;
      });

    rects
      .transition()
      .duration(t)
      .attr("width", x1ScaleCoin.rangeBand())
      .attr("x", function (d) {
        return x1ScaleCoin(d.side);
      })
      .attr("y", function (d) {
        return yScaleCoin(d.value);
      })
      .attr("height", function (d) {
        return yScaleCoin(1 - d.value);
      })
      .attr("class", function (d) {
        return d.side;
      });

    containerCoin.selectAll("g.Observed rect").each(function () {
      d3.select(this)
        .on("mouseover", tipCoinObs.show)
        .on("mouseout", tipCoinObs.hide);
    });
    containerCoin.selectAll("g.True rect").each(function () {
      d3.select(this)
        .on("mousedown", function (d) {
          tipCoinTheo.show(d, this);
        })
        .on("mouseover", function (d) {
          tipCoinTheo.show(d, this);
        })
        .on("mouseout", tipCoinTheo.hide)
        .call(dragCoin);
    });
    $("#barCoin").parent().on("mouseup", tipCoinTheo.hide);
  }

  function reset_clt() {
    clearInterval(interval_clt);
    counts = [];
    d3.timer.flush();
    svg_clt.selectAll("circle").remove();
    svg_clt.selectAll(".bar").remove();
    y_scale_clt.domain([0, 3]);
    draw_sampling();
  }

  function update() {
    var num = Math.random();
    if (num < probTheo[0]) {
      countCoin[0] = countCoin[0] + 1;
    } else {
      countCoin[1] = countCoin[1] + 1;
    }
    updateCoin(100);
  }

  // new
  d3.select("#try").on("input", function () {
    var n = this.value;
    d3.select("#try-value").text(n);
    reset_clt();
  });

  // new
  $("#form_chance").click(function () {
    var try_num = $("#try").val();
    var cnt = 0;
    var interval = setInterval(function () {
      update();
      if (++cnt == try_num) {
        clearInterval(interval);
      }
    }, 30); // 그래프 바뀌는 속도 수정
  });

  //Update SVG based on width of container
  function drawCoin() {
    var width = d3.select("#barCoin").node().getBoundingClientRect().width;
    var height = 550;
    var padCoin = 100;

    //Update SVG
    svgCoin
      //.attr("width", width)
      //.attr("height", height)
      .call(tipCoinObs)
      .call(tipCoinTheo);

    //Update Scales
    yScaleCoin.range([height - 2 * padCoin, 0]);
    x0ScaleCoin.rangeRoundBands([0, width], 0.1);
    x1ScaleCoin.rangeRoundBands([0, x0ScaleCoin.rangeBand()], 0.4);

    //Update Container
    containerCoin.attr("transform", "translate(" + 0 + "," + padCoin + ")");

    //Update Axis
    axisCoin
      .attr("transform", "translate(" + 0 + "," + (height - padCoin + 1) + ")")
      .call(xAxisCoin);

    //Update Rectangles
    updateCoin(0);
  }
  drawCoin();
  $(window).on("resize", drawCoin);
}

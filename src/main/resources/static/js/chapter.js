//JS functions used in all pages

//Adds bring to front for all elements from D3 selection
//Adapted from the following code:
//http://stackoverflow.com/questions/14167863/how-can-i-bring-a-circle-to-the-front-with-d3
d3.selection.prototype.moveToFront = function () {
  return this.each(function () {
    this.parentNode.appendChild(this);
  });
};

//Adds bring to back for all elements from D3 selection
d3.selection.prototype.moveToBack = function () {
  return this.each(function () {
    this.parentNode.insertBefore(this, this.parentNode.firstChild);
  });
};

//Rounds the input number to input decimal places
function round(number, decimal) {
  var power = Math.pow(10, decimal);
  return (Math.round(number * power) / power).toFixed(decimal);
}

//Additional Functions to JSTAT

jStat.binomialDiscrete = {};

jStat.binomialDiscrete.pdf = function (k, n, p) {
  if (k < 0 || !Number.isInteger(k) || k > n || p < 0 || p > 1) {
    return 0;
  } else {
    return jStat.binomial.pdf(k, n, p);
  }
};

jStat.binomialDiscrete.cdf = function (k, n, p) {
  return jStat.binomial.cdf(k, n, p);
};

jStat.binomialDiscrete.mean = function (n, p) {
  return n * p;
};

jStat.binomialDiscrete.sample = function (n, p) {
  var sum = 0;
  for (var i = 0; i < n; i++) {
    sum += +(Math.random() < p);
  }
  return sum;
};

jStat.bernoulli = {};

jStat.bernoulli.pdf = function (k, p) {
  return jStat.binomialDiscrete.pdf(k, 1, p);
};

jStat.bernoulli.cdf = function (k, p) {
  return jStat.binomial.cdf(k, 1, p);
};

jStat.bernoulli.mean = function (p) {
  return p;
};

jStat.bernoulli.sample = function (p) {
  return +(Math.random() < p);
};

jStat.negbin.mean = function (r, p) {
  return ((1 - p) * r) / p;
};

jStat.geometric = {};

jStat.geometric.pdf = function (k, p) {
  if (k < 0 || !Number.isInteger(k)) {
    return 0;
  } else {
    return Math.pow(1 - p, k) * p;
  }
};

jStat.geometric.cdf = function (k, p) {
  if (k < 0) {
    return 0;
  } else {
    return 1 - Math.pow(1 - p, Math.floor(k) + 1);
  }
};

jStat.geometric.mean = function (p) {
  return (1 - p) / p;
};

jStat.poisson.mean = function (lambda) {
  return lambda;
};

// Slider
function create_slider(slide, svg, width, height, margin) {
  var x = d3.scale.linear().domain([0, 1]).range([0, width]).clamp(true);

  var drag = d3.behavior.drag().on("drag", function (d, i) {
    var val = x.invert(d3.event.x);
    handle.attr("cx", x(val));
    slide(val);
  });

  var slider = svg
    .append("g")
    .attr("class", "range")
    .attr("transform", "translate(" + margin + "," + height + ")");

  slider
    .append("line")
    .attr("class", "track")
    .attr("x1", x.range()[0])
    .attr("x2", x.range()[1])
    .select(function () {
      return this.parentNode.appendChild(this.cloneNode(true));
    })
    .attr("class", "track-inset")
    .select(function () {
      return this.parentNode.appendChild(this.cloneNode(true));
    })
    .attr("class", "track-overlay")
    .call(drag);

  slider
    .insert("g", ".track-overlay")
    .attr("class", "ticks")
    .attr("transform", "translate(0," + 25 + ")")
    .selectAll("text")
    .data(x.ticks(10))
    .enter()
    .append("text")
    .attr("x", x)
    .attr("text-anchor", "middle")
    .text(function (d) {
      return d;
    });

  var handle = slider
    .insert("circle", ".track-overlay")
    .attr("class", "handle")
    .attr("r", 12);

  function reset() {
    handle.attr("cx", x(0));
  }

  return reset;
}

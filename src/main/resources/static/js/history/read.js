let mainTable = '';
const read = {
    init: function () {
        const self = this;
        self.initUI();
    },
    initUI: function () {
        const self = this;
        const mainTableName = '#history_list';
        //const mainTable = new Vue({
        mainTable = new Vue({
            el: mainTableName,
            data: function () {
                return {
                    table: {
                        crawledProbTable: '',
                        userProbTable: ''
                    },
                    crawled: {
                        display: false
                    },
                    price: {
                        selected: ''
                    },
                    game: {
                        selected: ''
                    },
                    item: {
                        list: [
                            {
                                id: '-1',
                                itemname: '게임을 먼저 선택해주세요'
                            }
                        ],
                        selected: -1
                    },
                    gacha: {
                        selected: ''
                    },
                    chart: {
                        bar: {
                            graph: {},
                            zero: 0,
                            one: 0
                        },
                        area: {
                            graph: {},
                            eightyfive: 0,
                            price: 0,
                            hoverCnt: 0,
                            hoverProb: 0
                        }
                    }
                };
            },
            mounted: function () {
                let gacha_id = $('#gacha_id').val();
                this.CreateUserProbTable(gacha_id);
                let chartData = $('#outputcalcjson').val();
                // console.log(gacha_id);
                 console.log(chartData);

                if (gacha_id == 1) {
                    this.UpdateCompleteGachaCharts(chartData);
                } else {
                    this.UpdateCharts(chartData);
                }
                // console.log(this.table.userProbTable);
                // this.userProbTable.spreadsheet.setValueFromCoords(3, 3, '3');
                // DEBUG.table.userProbTable.spreadsheet.setValueFromCoords(3, 3, '3');
            },
            methods: {
                UpdateCharts: function (chartData) {
                    chartData = chartData.replace(/(['"])?([a-zA-Z0-9]+)(['"])?:/g, '"$2":');
                    var arr = chartData.split('},');
                    console.log(chartData);
                    console.log(arr);
                    var barChartData = JSON.parse(arr[0].trim());
                    var areaChartData = JSON.parse(arr[1].trim());

                    this.BarChart(barChartData);
                    this.AreaChart(areaChartData);
                },
                UpdateCompleteGachaCharts: function (chartData) {
                    chartData = chartData.replace(/(['"])?([a-zA-Z0-9]+)(['"])?:/g, '"$2":');
                    var arr = chartData.split('},');
                    console.log(arr);
                    console.log(chartData);
                    var areaChartData = JSON.parse(arr[0].trim());

                    this.AreaChart(areaChartData);
                },
                CreateUserProbTable: function (gachaid) {
                    const userTableName = '#userProbTable';
                    $(userTableName).empty();
                    var format = '';
                    this.table.userProbTable = new Vue({
                        el: userTableName,
                        data: function () {
                            return {spreadsheet: {}}
                        },
                        mounted: function () {
                            this.spreadsheet = jspreadsheet(this.$el, {
                                minDimensions: [
                                    30, 30
                                ],
                                defaultColWidth: 100,
                                tableOverflow: true,
                                tableWidth: "100%",
                                csv: '/assets/selection/' + gachaid + '.csv',
                                csvHeaders: false,
                                defaultColAlign: 'left',
                                editable: false
                            });
                        }
                    });
                },
                CalcProb: function () {
                    // History.Request
                    const data = {
                        title: '',
                        writer: $('#inputprobcsv').val(),
                        inputprobcsv: $('#inputprobcsv').val(),
                        outputcalcjson: $('#outputcalcjson').val(),
                        item_id: $('#item_id').val(),
                        itemname: $('#itemname').val(),
                        game_id: $('#game_id').val(),
                        gachaname: $('#gachaname').val(),
                        gacha_id: $('#gacha_id').val(),
                        gachaname: $('#gachaname').val(),
                        price: $('#price').val()
                    };

                    console.log(data);

                    $
                        .ajax({
                            type: 'GET',
                            url: '/history/calc',
                            contentType: 'application/json; charset=utf-8',
                            data: JSON.stringify(data),
                            success: function (retval) {
                                alert(retval);
                            }
                        })
                        .done(function () {
                            alert('등록되었습니다.');
                        })
                        .fail(function (error) {
                            alert(JSON.stringify(error));
                        });

                    return;
                },
                SimProb: function () {
                    // History.Request
                    const data = {
                        title: '',
                        writer: $('#inputprobcsv').val(),
                        inputprobcsv: $('#inputprobcsv').val(),
                        outputcalcjson: $('#outputcalcjson').val(),
                        item_id: $('#item_id').val(),
                        itemname: $('#itemname').val(),
                        game_id: $('#game_id').val(),
                        gachaname: $('#gachaname').val(),
                        gacha_id: $('#gacha_id').val(),
                        gachaname: $('#gachaname').val(),
                        price: $('#price').val()
                    };

                    console.log(data);

                    $
                        .ajax({
                            type: 'POST',
                            url: '/history/sim',
                            dataType: 'JSON',
                            contentType: 'application/json; charset=utf-8',
                            data: JSON.stringify(data),
                            success: function (data) {
                                // window.location.href = '/history/list';

                            }
                        })
                        .done(function (retval) {
                            alert(retval);
                            // window.location.href = '/history/list';
                        })
                        .fail(function (error) {
                            alert(JSON.stringify(error));
                        });

                    return;
                },
                BarChart: function (barChartData) {
                    var self = this;
                    self.chart.bar.zero = Math.round(barChartData[0] * 10000) / 100;
                    self.chart.bar.one = 100 - self.chart.bar.zero;
                    // Set new default font family and font color to mimic Bootstrap's default
                    // styling
                    $('#myBarChart').remove();
                    $('#barChartDiv').append(
                        '<canvas id="myBarChart" width="100" height="40"></canvas>'
                    );

                    Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",' +
                            'Arial,sans-serif';
                    Chart.defaults.global.defaultFontColor = '#292b2c';

                    // Bar Chart Example
                    var ctx = document.getElementById("myBarChart");
                    self.chart.bar.graph = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: Object.keys(barChartData),
                            datasets: [
                                {
                                    label: "Revenue",
                                    backgroundColor: "rgba(2,117,216,1)",
                                    borderColor: "rgba(2,117,216,1)",
                                    data: Object.values(barChartData)
                                }
                            ]
                        },
                        options: {
                            scales: {
                                xAxes: [
                                    {
                                        gridLines: {
                                            display: false
                                        },
                                        ticks: {
                                            maxTicksLimit: 6
                                        }
                                    }
                                ],
                                yAxes: [
                                    {
                                        ticks: {
                                            min: 0,
                                            max: 1,
                                            maxTicksLimit: 5
                                        },
                                        gridLines: {
                                            display: true
                                        }
                                    }
                                ]
                            },
                            legend: {
                                display: false
                            }
                        }
                    });
                },
                AreaChart: function (areaChartData) {

                    var self = this;

                    // set price
                    self.chart.area.price = $('#price').val();

                    // console.log(areaChartData);
                    for (const [key, value] of Object.entries(areaChartData)) {

                        console.log(`${key}: ${value}`);
                        if (0.85 <= value) {
                            self.chart.area.eightyfive = key;
                            self.chart.area.hoverCnt = key;
                            self.chart.area.hoverProb = Math.round(value * 10000) / 100;
                            break;
                        }
                    }

                    $('#myAreaChart').remove();
                    $('#areaChartDiv').append(
                        '<canvas id="myAreaChart" width="100" height="40"></canvas>'
                    );

                    Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",' +
                            'Arial,sans-serif';
                    Chart.defaults.global.defaultFontColor = '#292b2c';

                    // Area Chart Example
                    var ctx = document.getElementById("myAreaChart");
                    self.chart.area.graph = new Chart(ctx, {
                        type: 'line',
                        data: {
                            labels: Object.keys(areaChartData),
                            datasets: [
                                {
                                    label: "Sessions",
                                    lineTension: 0.3,
                                    backgroundColor: "rgba(2,117,216,0.2)",
                                    borderColor: "rgba(2,117,216,1)",
                                    pointRadius: 5,
                                    pointBackgroundColor: "rgba(2,117,216,1)",
                                    pointBorderColor: "rgba(255,255,255,0.8)",
                                    pointHoverRadius: 5,
                                    pointHoverBackgroundColor: "rgba(2,117,216,1)",
                                    pointHitRadius: 50,
                                    pointBorderWidth: 2,
                                    data: Object.values(areaChartData)
                                }
                            ]
                        },
                        options: {
                            scales: {
                                xAxes: [
                                    {
                                        gridLines: {
                                            display: false
                                        },
                                        ticks: {
                                            maxTicksLimit: 7
                                        }
                                    }
                                ],
                                yAxes: [
                                    {
                                        ticks: {
                                            min: 0,
                                            max: 1,
                                            maxTicksLimit: 5
                                        },
                                        gridLines: {
                                            color: "rgba(0, 0, 0, .125)"
                                        }
                                    }
                                ]
                            },
                            legend: {
                                display: false
                            },
                            onHover: function (evt) {
                                var item = self
                                    .chart
                                    .area
                                    .graph
                                    .getElementAtEvent(evt);
                                if (item.length) {
                                    let tryCnt = item[0]._index;
                                    let prob = self
                                        .chart
                                        .area
                                        .graph
                                        .data
                                        .datasets[0]
                                        .data[item[0]._index];
                                    self.chart.area.hoverCnt = tryCnt;
                                    self.chart.area.hoverProb = Math.round(prob * 10000) / 100;
                                }
                            }
                        }
                    });

                }
            }
        });
    }
}

read.init();

$(document).ready(function () {

    //db로부터 가져온 값
    let inputProb = JSON.parse($('#inputprobcsv').val());

    // 템플릿값
    let temp = mainTable
        .table
        .userProbTable
        .spreadsheet
        .getData()
    let header = temp[1];

    // db로부터 가져온 값 foreach 돌면서 테이블에 넣기
    Object
        .entries(inputProb)
        .forEach(([key, value]) => {
            let row = header.indexOf(key);

            if (Array.isArray(value)) {
                value.forEach((v, i) => {
                    mainTable
                        .table
                        .userProbTable
                        .spreadsheet
                        .setValueFromCoords(row, i + 2, v);
                })
            } else {
                mainTable
                    .table
                    .userProbTable
                    .spreadsheet
                    .setValueFromCoords(row, 2, value);
            }
        });

});
var DEBUG = '';
const read = {
    init: function () {
        const self = this;
        self.initUI();
    },
    initUI: function () {
        const self = this;
        const mainTableName = '#history_list';
        //const mainTable = new Vue({
        DEBUG = new Vue({
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
                    ddd: {},
                };
            },
            mounted: function () {
                this.CreateUserProbTable($('#gacha_id').val());
            },
            methods: {
                UpdateCharts: function (chartData) {
                    chartData = chartData.replace(/(['"])?([a-zA-Z0-9]+)(['"])?:/g, '"$2":');
                    var arr = chartData.split('},');
                    //console.log(arr);
                    var barChartData = JSON.parse(arr[0].trim());
                    var areaChartData = JSON.parse(arr[1].trim());

                    this.BarChart(barChartData);
                    this.AreaChart(areaChartData);
                },
                UpdateCompleteGachaCharts: function (chartData) {
                    chartData = chartData.replace(/(['"])?([a-zA-Z0-9]+)(['"])?:/g, '"$2":');
                    var arr = chartData.split('},');
                    //console.log(arr);
                    var areaChartData = JSON.parse(arr[0].trim());

                    this.AreaChart(areaChartData);
                },
                CreateUserProbTable: function (gachaid) {
                    const userTableName = '#userProbTable';
                    $(userTableName).empty();
                    this.table.userProbTable = new Vue({
                        el: userTableName,
                        data: function () {
                            return {
                                spreadsheet: {},
                            }
                        },
                        mounted: function () {
                            this.spreadsheet = jspreadsheet(this.$el, {
                                minDimensions: [
                                    6, 30
                                ],
                                defaultColWidth: 100,
                                tableOverflow: true,
                                tableWidth: "100%",
                                csv: '/assets/selection/' + gachaid + '.csv',
                                csvHeaders: false,
                                editable: false
                            });
                            Object.assign(this, this.spreadsheet);
                        }
                    });
                },
                BarChart: function (barChartData) {
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
                    var myLineChart = new Chart(ctx, {
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
                    // console.log(Object.keys(areaChartData));
                    // console.log(Object.values(areaChartData)); Set new default font family and
                    // font color to mimic Bootstrap's default styling
                    $('#myAreaChart').remove();
                    $('#areaChartDiv').append(
                        '<canvas id="myAreaChart" width="100" height="40"></canvas>'
                    );

                    Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",' +
                            'Arial,sans-serif';
                    Chart.defaults.global.defaultFontColor = '#292b2c';

                    // Area Chart Example
                    var ctx = document.getElementById("myAreaChart");
                    var myLineChart = new Chart(ctx, {
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
                            }
                        }
                    });

                },
            }
        });
    }
}

read.init();
console.log(DEBUG);
DEBUG.table.userProbTable.spreadsheet.setValueFromCoords(3, 3, '3');
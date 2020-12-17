module.exports = {
  lintOnSave: false,
  configureWebpack: {
    devServer: {
      headers: {
        "Access-Control-Allow-Origin": "*",
      },
      disableHostCheck: true,
      sockPort: 8501,
      https: false,
      sockHost: "localhost",
    },
    externals: ["vue", "vue-router", /^@vue-app-mod\/.+/],
  },
  filenameHashing: false,
};

const path = require("path");
const { CleanWebpackPlugin } = require("clean-webpack-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = (env) => ({
  entry: path.resolve(__dirname, "src/vue-app-mod-shell"),
  output: {
    filename: "vue-app-mod-shell.js",
    libraryTarget: "system",
    path: path.resolve(__dirname, "dist"),
  },
  devtool: "sourcemap",
  module: {
    rules: [
      { parser: { system: false } },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: [{ loader: "babel-loader" }],
      },
      {
        test: /\.(png|svg|jpg|jpeg|gif)$/i,
      },
    ],
  },
  devServer: {
    headers: {
      "Access-Control-Allow-Origin": "*",
    },
    disableHostCheck: true,
    historyApiFallback: true,
  },
  plugins: [
    new HtmlWebpackPlugin({
      inject: false,
      template: "src/index.ejs",
      templateParameters: {
        isLocal: env && env.isLocal === "true",
      },
    }),
    new CleanWebpackPlugin(),
  ],
  externals: ["single-spa", "vue", "vue-router", /^@vue-mf\/.+$/],
});

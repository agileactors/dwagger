document.addEventListener("DOMContentLoaded", function () {
  const doc = document;
  window.jsPDF = window.jspdf.jsPDF;
  window.html2canvas = html2canvas;

  function download() {
    const divCanvas = doc.getElementById("canvas_div_pdf");

    html2canvas(divCanvas, {
      scale: "2",
    }).then(function (canvas) {
      const img = canvas.toDataURL("image/jpeg");
      const pdf = new jsPDF("p", "px", [
        divCanvas.clientWidth,
        divCanvas.clientHeight,
      ]);

      const width = pdf.internal.pageSize.getWidth();
      let height = pdf.internal.pageSize.getHeight();

      pdf.addImage(img, "JPEG", 60, 0, width - 150, height - 10);
      pdf.save("documentation_file.pdf");
    });
  }

  const button = doc.getElementById("download");
  button.addEventListener("click", (ev) => {
    download();
  });
});

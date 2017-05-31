(ns pretty-csv.core
  (:gen-class)
  (:require [clojure.data.csv :as csv]
     [clojure.java.io :as io]))

(defn exit-with-mesg
  [mesg]
  (println mesg)
  (System/exit 0))

(defn len-b
  "count half-byte character length of s"
  [s]
  (count (.getBytes s "MS932")))

(defn get-column-length-list
  [csv-file-path]
  (with-open [reader (io/reader csv-file-path)]
    (reduce (fn
              [max-len-list line]
              (cond (empty? max-len-list) (map len-b line)
                :else (map #(apply max %) (map vector (map len-b line) max-len-list)))) [] (csv/read-csv reader))))

(defn prettify-csv
  "prettify and print csv data"
  [csv-file-path]
  (cond (not (.exists (io/as-file csv-file-path)))
    (exit-with-mesg "file does not exist"))
  (let [col-len-list (get-column-length-list csv-file-path)]
    (with-open [reader (io/reader csv-file-path)]
      (doseq [line (csv/read-csv reader)]
        (print "| ")
        (doseq [[i chunk] (map-indexed vector line)]
          (print (format (str "%-" (- (nth col-len-list i) (- (len-b chunk) (count chunk))) "s | ") chunk)))
        (println)))))

(defn -main
  [& args]
  (cond (= (count args) 0)
    (exit-with-mesg "give a csv file path"))
  (prettify-csv (first args)))

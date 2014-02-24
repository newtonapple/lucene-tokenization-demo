package ddai.lucene.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.icu.ICUFoldingFilter;
import org.apache.lucene.analysis.icu.segmentation.ICUTokenizer;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.util.Version;

import java.io.Reader;

/**
 * Created by divi on 2/24/14.
 */
public class ICUShingleAnalyzer extends Analyzer {
    private int minShingleSize, maxShingleSize;

    public ICUShingleAnalyzer() {
        this.minShingleSize = 2;
        this.maxShingleSize = 2;
    }

    public ICUShingleAnalyzer(int maxShingleSize) {
        this.minShingleSize = 2;
        this.maxShingleSize = maxShingleSize;
    }

    public ICUShingleAnalyzer(int minShingleSize, int maxShingleSize) {
        this.minShingleSize = minShingleSize;
        this.maxShingleSize = maxShingleSize;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        Tokenizer source = new ICUTokenizer(reader);
        TokenStream filter = new ShingleFilter(
                new StopFilter(Version.LUCENE_46, new ICUFoldingFilter(source), StopAnalyzer.ENGLISH_STOP_WORDS_SET),
            this.minShingleSize,
            this.maxShingleSize
        );
        return new TokenStreamComponents(source, filter);
    }
}
